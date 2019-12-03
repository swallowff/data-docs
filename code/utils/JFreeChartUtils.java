package cn.huapu.power.energy.util;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @Author: shenyu
 * @Date: 2019-12-3 9:22
 * @Description:
 * @docs: http://www.360doc.com/content/13/0311/15/5224731_270804125.shtml
 */
public class JFreeChartUtils {

    public static DefaultCategoryDataset defaultCategoryDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "rowKey1", "苹果");
        dataset.addValue(2, "rowKey2", "梨子");
        dataset.addValue(3, "rowKey3", "葡萄");
        dataset.addValue(4, "rowKey3", "香蕉");
        dataset.addValue(5, "rowKey3", "西瓜");
        return dataset;
    }

    public static DefaultPieDataset defaultPieDataset(){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("苹果",100);
        dataset.setValue("香蕉",200);
        dataset.setValue("西瓜",300);
        return dataset;
    }

    public static void drawJPEG(JFreeChart jFreeChart, File file, int width, int height) throws IOException {
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            file.createNewFile();
        }
        ChartUtilities.saveChartAsJPEG( file , jFreeChart , width , height );
    }

    /**
     * @Author: shenyu
     * @Date: 2019-12-3 15:27
     * @Description: 测试-饼图
     */
    public static void testPieChart(File file){
        DefaultPieDataset dataset = defaultPieDataset();
        JFreeChart chart = ChartFactory.createPieChart("测试饼图",dataset,true,false,false);
        //重新设置标题字体,避免中文乱码
        chart.getTitle().setFont(new Font("黑体", Font.ITALIC, 15));

        CategoryPlot plot = chart.getCategoryPlot();

        try {
            drawJPEG(chart,file,300,300);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: shenyu
     * @Date: 2019-12-3 15:28
     * @Description: 测试-柱状图
     */
    public static void testBarChart(File file){
        DefaultCategoryDataset dataset = JFreeChartUtils.defaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart3D(
                "水果产量图",        // 图表标题
                "水果",  // 目录轴的显示标签
                "产量",     // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true,             // 是否显示图例(对于简单的柱状图必须是false)
                false,            // 是否生成工具
                false);              // 是否生成URL链接

        BarRenderer renderer = new BarRenderer();
        renderer.setMaximumBarWidth(0.05);
        renderer.setMinimumBarLength(0.2);

        renderer.setSeriesPaint(0,Color.decode("#00BF00"));

        //大多数情况下都需要在柱子上显示对应的数值，设置如下
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        //设置数值颜色，默认黑色
        renderer.setBaseItemLabelPaint(Color.BLUE);
        renderer.setItemLabelAnchorOffset(2);

        //设置标题
        chart.getTitle().setFont(new Font("隶书", Font.ITALIC, 15));
        //设置图例类别字体
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot categoryPlot = chart.getCategoryPlot();//用于设置显示特性
        categoryPlot.setBackgroundPaint(Color.WHITE);
        categoryPlot.setDomainGridlinePaint(Color.BLACK);//分类轴网格线条颜色
        categoryPlot.setDomainGridlinesVisible(true);
        categoryPlot.setRangeGridlinePaint(Color.GREEN);//数据轴网格线条颜色

        //横纵坐标轴设置
        CategoryAxis domainAxis=categoryPlot.getDomainAxis();   //水平底部列表
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,5)); //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,10)); //垂直标题
        ValueAxis rangeAxis=categoryPlot.getRangeAxis();    //获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,5)); //设置柱状标题

        CategoryPlot plot = chart.getCategoryPlot();
        // 设置柱图背景色
        plot.setBackgroundPaint(new Color(255, 247, 229));

        plot.setRenderer(renderer);
        try {
            drawJPEG(chart,file,400,400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author: shenyu
     * @Date: 2019-12-3 15:27
     * @Description: file转base64
     */
    public static String base64Encode(File file) throws IOException{
        byte[] bytes = FileUtils.readFileToByteArray(file);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    public static void main(String[] args) throws Exception{
        File file = new File( "/data/charts/test3.jpg" );
        JFreeChartUtils.testPieChart(file);
    }

}
