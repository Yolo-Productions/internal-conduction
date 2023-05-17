package br.com.yolo.core.account.statistic;

import br.com.yolo.core.account.datahandler.DataHandler;
import lombok.Getter;

import java.util.Map;

public enum StatisticType implements StatisticBoard {

    DAILY("dailyStatistics") {

        @Override
        public Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler) {
            return dataHandler.getDailyStatistics();
        }
    },

    WEEKLY("weeklyStatistics") {

        @Override
        public Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler) {
            return dataHandler.getWeeklyStatistics();
        }
    },

    MONTLY("montlyStatistics") {

        @Override
        public Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler) {
            return dataHandler.getMontlyStatistics();
        }
    },

    FULL("fullyStatistics") {

        @Override
        public Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler) {
            return dataHandler.getFullyStatistics();
        }
    };

    @Getter private static String defaultMySQLOrderByQuery = "SELECT * FROM `profile_holder`" +
            " ORDER BY " + "CAST(JSON_UNQUOTE(JSON_EXTRACT(`json`, " +
            "'$.dataHandler.%map-name%.%s.value')) AS INT)" +
            " DESC LIMIT %d;";

    @Getter private final String statisticMapName;
    @Getter private final String mysqlOrderByQuery;

    StatisticType(String statsMapName) {
        this.statisticMapName = statsMapName;
        this.mysqlOrderByQuery = getDefaultMySQLOrderByQuery().replace("%map-name%", statsMapName);
    }

    public String formatOrderByQuery(StatisticTag tag, int max) {
        return String.format(getMysqlOrderByQuery(), tag.toString(), max);
    }
}
