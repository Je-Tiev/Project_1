package clamav.service;

import clamav.model.ScanResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsService {

    public ScanStatistics calculateStatistics(List<ScanResult> scanResults) {
        long totalScanned = scanResults.size();
        long cleanCount = scanResults.stream().filter(ScanResult::isClean).count();
        long infectedCount = scanResults.stream().filter(r -> r.getStatus().equals("INFECTED")).count();
        long errorCount = scanResults.stream().filter(r -> r.getStatus().equals("ERROR")).count();

        Map<String, Long> scanTypeCounts = scanResults.stream()
                .collect(Collectors.groupingBy(ScanResult::getScanType, Collectors.counting()));
        Map<String, Long> statusCounts = scanResults.stream()
                .collect(Collectors.groupingBy(ScanResult::getStatus, Collectors.counting()));


        return new ScanStatistics(totalScanned, cleanCount, infectedCount, errorCount, scanTypeCounts, statusCounts);
    }
    public record ScanStatistics(long totalScanned, long cleanCount, long infectedCount, long errorCount,
                                 Map<String, Long> scanTypeCounts,
                                 Map<String, Long> statusCounts){
    }
}