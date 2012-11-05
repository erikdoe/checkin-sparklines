package com.thoughtworks.scmviz.model;

import org.joda.time.ReadableDateTime;

import java.util.*;

public class Commit {

    public static final String ANONYMOUS = "anonymous";

    private final ReadableDateTime timestamp;
    private final String committer;

    public Commit(ReadableDateTime timestamp, String committer) {
        this.timestamp = timestamp;
        this.committer = committer;
    }

    public ReadableDateTime getTimestamp() {
        return timestamp;
    }

    public String getCommitter() {
        return committer;
    }


    public interface FilterFunction {
        boolean eval(Commit commit);
    }

    public static List<Commit> filter(List<Commit> commits, FilterFunction filterFunction) {
        List<Commit> result = new ArrayList<Commit>();
        for(Commit c : commits) {
            if(filterFunction.eval(c)) {
                result.add(c);
            }
        }
        return result;
    }


    public interface GroupFunction<T> {
        T eval(Commit commit);
    }

    public static <T> Map<T, List<Commit>> group(List<Commit> commits, GroupFunction<T> groupFunction) {
         Map<T, List<Commit>> result = new HashMap<T, List<Commit>>();
         for (Commit c : commits) {
             List<Commit> group = result.get(groupFunction.eval(c));
             if (group == null) {
                 group = new ArrayList<Commit>();
                 result.put(groupFunction.eval(c), group);
             }
             group.add(c);
         }
         return result;
     }



}
