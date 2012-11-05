package com.thoughtworks.scmviz.main;

import com.thoughtworks.scmviz.model.Project;
import com.thoughtworks.scmviz.model.Repository;
import com.thoughtworks.scmviz.scm.SubversionService;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws Exception {

        Repository repository = new Repository(args[0]);
        if(args.length > 2)  {
            repository.setUsername(args[1]);
            repository.setPassword(args[2]);
        }
        String outputPath = "output";

        new Runner().run(outputPath, repository);
    }

    private void run(String outputPath, Repository repository) throws Exception {
        Project project = new Project(repository);

        SubversionService subversionService = new SubversionService();
        project.addCommits(subversionService.retrieveCommits(project.getRepository()));

        prepareOutputDirectory(outputPath);
        LocalDate endDate = calculateEndDate();

        PageWriter writer = new PageWriter(project, endDate, outputPath);
        writer.writePageForProject();
        writer.writeImagesForProject();
    }

    private void prepareOutputDirectory(String path) throws IOException {
        File directory = new File(path);
        FileUtils.deleteDirectory(directory);
        if (!directory.mkdirs()) {
            throw new IOException("Failed to create output directory: " + directory.getAbsoluteFile());
        }
        FileUtils.copyURLToFile(getClass().getResource("/default.css"), new File(path + "/default.css"));
    }

    private LocalDate calculateEndDate() {
        LocalDate now = new LocalDate(new DateTime().getYear(), new DateTime().getMonthOfYear(), 1);
        if (new DateTime().getDayOfMonth() > 1) {
            now = now.plusMonths(1);
        }
        return now;
    }

}
