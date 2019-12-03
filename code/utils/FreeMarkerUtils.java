package cn.huapu.power.energy.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Map;

/**
 * @Author: shenyu
 * @Date: 2019-12-2 11:43
 * @Description:
 */
public class FreeMarkerUtils {
    private static Configuration configuration;
    public static final String TEMPLATE_DIR = "/templates";

    /**
     * @Author: shenyu
     * @Date: 2019-12-3 15:30
     * @Description: 获取freeMarker配置
     */
    public static Configuration getConfiguration(){
        if (null == configuration){
            configuration = new Configuration(Configuration.VERSION_2_3_0);
            try {
                configuration.setDirectoryForTemplateLoading(new File(ResourceUtils.getURL("classpath:").getPath() + "templates"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configuration;
    }

    /**
     * @Author: shenyu
     * @Date: 2019-12-3 15:30
     * @Description: 渲染模板
     */
    public static File render(Map<String,Object> dataMap, String templateName, String dir, String fileName){
        Writer writer = null;
        File outFile = new File(dir + File.separator + fileName);
        try {
            Configuration configuration = FreeMarkerUtils.getConfiguration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(FreeMarkerUtils.class,TEMPLATE_DIR);
            Template template = configuration.getTemplate(templateName);
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new FileWriter(outFile));
            template.process(dataMap,writer);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            StreamUtils.close(writer);
        }
        return outFile;
    }

    public static File render(Object dataBean, String templateName, String dir, String fileName){
        File file = null;
        try {
            file = render(PropertyUtils.describe(dataBean),templateName,dir,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
