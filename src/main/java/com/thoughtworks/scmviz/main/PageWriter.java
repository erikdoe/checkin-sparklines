package com.thoughtworks.scmviz.main;

import com.thoughtworks.scmviz.model.Developer;
import com.thoughtworks.scmviz.model.Project;
import com.thoughtworks.scmviz.sparkline.DataPoint;
import com.thoughtworks.scmviz.sparkline.Sparkline;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.joda.time.LocalDate;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PageWriter {

    private static final int MONTHS = 24;
    private static final int CAP = 30;

    private final Project project;
    private final LocalDate endDate;
    private final String outputPath;

    public PageWriter(Project project, LocalDate endDate, String outputPath) {
        this.project = project;
        this.endDate = endDate;
        this.outputPath = outputPath;
    }

    public void writePageForProject() throws IOException, TemplateException {
        HashMap<String, Object> model = new HashMap<String, Object>();
        model.put("project", project);
        model.put("developers", project.getAllDevelopers());
        model.put("cap", CAP);
        model.put("endDate", endDate);
        model.put("startDate", endDate.minusMonths(MONTHS));

        String pagePath = String.format("%s/index.html", outputPath);
        Writer writer = new FileWriter(pagePath);
        getTemplate().process(model, writer);
        writer.flush();
    }

    private Template getTemplate() throws IOException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(getClass(), "/");
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        return configuration.getTemplate("committers.ftl");
    }

    public void writeImagesForProject() throws IOException {
        for (Developer developer : project.getAllDevelopers()) {
            writeImageForDeveloper(developer);
        }
    }

    private void writeImageForDeveloper(Developer developer) throws IOException {
        Map<LocalDate, Integer> commitsByMonth = developer.getNumberOfCommitsByMonth();

        Sparkline sparkline = new Sparkline(MONTHS, CAP);
        ArrayList<DataPoint> points = new ArrayList<DataPoint>();
        LocalDate now = endDate;
        for (int i = MONTHS; i > 0; i--) {
            LocalDate then = now.minusMonths(i);
            points.add(new DataPoint(commitsByMonth.get(then), then.getMonthOfYear() == 1));
        }
        RenderedImage image = sparkline.render(points);

        String imagePath = String.format("%s/sparkline-%s.png", outputPath, developer.getName());
        ImageIO.write(image, "png", new File(imagePath));
    }

    private void writeImageForDeveloperByWeek(Developer developer) throws IOException {
        Map<Integer, Integer> commitsByWeek = developer.getNumberOfCommitsByWeek();

        Sparkline sparkline = new Sparkline(MONTHS*42/10, CAP);
        ArrayList<DataPoint> points = new ArrayList<DataPoint>();
        LocalDate now = endDate;
        LocalDate then = endDate.minusMonths(MONTHS);
        while(!then.isAfter(now)) {
            points.add(new DataPoint(commitsByWeek.get(then.getWeekyear()*100 + then.getWeekOfWeekyear()), then.getMonthOfYear() == 1));
            then = then.plusWeeks(1);
        }
        RenderedImage image = sparkline.render(points);

        String imagePath = String.format("%s/sparkline-%s.png", outputPath, developer.getName());
        ImageIO.write(image, "png", new File(imagePath));
    }

}
