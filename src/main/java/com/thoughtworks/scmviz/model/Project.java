package com.thoughtworks.scmviz.model;

import java.util.*;


public class Project {

    private final Repository repository;
    private final List<Commit> commits;

    public Project(Repository repository) {
        this.repository = repository;
        this.commits = new ArrayList<Commit>();
    }

    public Repository getRepository() {
        return repository;
    }

    public void addCommits(List<Commit> newCommits) {
        commits.addAll(newCommits);
    }

    public Developer getDeveloper(final String name) {
        List<Commit> filteredCommits = Commit.filter(commits, new Commit.FilterFunction() {
            public boolean eval(Commit commit) {
                return commit.getCommitter().equals(name);
            }
        });
        return new Developer(name, filteredCommits);
    }

    public List<Developer> getAllDevelopers() {
        Map<String, List<Commit>> groupedCommits = Commit.group(commits, new Commit.GroupFunction<String>() {
            public String eval(Commit commit) {
                return commit.getCommitter();
            }
        });
        List<Developer> result = new ArrayList<Developer>();
        for (Map.Entry<String, List<Commit>> entry : groupedCommits.entrySet()) {
            result.add(new Developer(entry.getKey(), entry.getValue()));
        }
        Collections.sort(result, new Comparator<Developer>() {
            public int compare(Developer a, Developer b) {
                return b.getNumberOfCommits() - a.getNumberOfCommits();
            }
        });
        return result;
    }

}
