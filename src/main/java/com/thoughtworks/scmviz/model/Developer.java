package com.thoughtworks.scmviz.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.*;

public class Developer {

    private final String name;
    private final List<Commit> commits;

    public Developer(String name, List<Commit> commits) {
        this.name = name;
        this.commits = commits;
    }

    public String getName() {
        return name;
    }

    public List<Commit> getCommits() {
        return Collections.unmodifiableList(commits);
    }

    public int getNumberOfCommits() {
        return commits.size();
    }

    public int getNumberOfRecentCommits() {
        DateTime cutoffDate = new DateTime().minusMonths(1);
        int count = 0;
        for (Commit c : commits) {
            if (c.getTimestamp().isAfter(cutoffDate)) {
                count += 1;
            }
        }
        return count;
    }

    public Map<LocalDate, Integer> getNumberOfCommitsByMonth() {
        Map<LocalDate, List<Commit>> grouped = Commit.group(commits, new Commit.GroupFunction<LocalDate>() {
            public LocalDate eval(Commit commit) {
                return new LocalDate(commit.getTimestamp().getYear(), commit.getTimestamp().getMonthOfYear(), 1);
            }
        });
        Map<LocalDate, Integer> counts = new HashMap<LocalDate, Integer>();
        for (Map.Entry<LocalDate, List<Commit>> entry : grouped.entrySet()) {
            counts.put(entry.getKey(), entry.getValue().size());
        }
        return counts;
    }

        public Map<Integer, Integer> getNumberOfCommitsByWeek() {
        Map<Integer, List<Commit>> grouped = Commit.group(commits, new Commit.GroupFunction<Integer>() {
            public Integer eval(Commit commit) {
                return commit.getTimestamp().getWeekyear() * 100 + commit.getTimestamp().getWeekOfWeekyear();
            }
        });
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, List<Commit>> entry : grouped.entrySet()) {
            counts.put(entry.getKey(), entry.getValue().size());
        }
        return counts;
    }
}
