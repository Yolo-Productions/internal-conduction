package br.com.yolo.core.player.account.datahandler;

import br.com.yolo.core.player.account.setting.Setting;
import br.com.yolo.core.player.account.setting.SettingTag;
import br.com.yolo.core.player.account.statistic.Statistic;
import br.com.yolo.core.player.account.statistic.StatisticTag;
import br.com.yolo.core.player.account.statistic.StatisticType;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class DataHandler {

    @Getter
    private UUID playerUniqueId;
    /**
     * Represents player daily statistics
     */
    @Getter private LinkedHashMap<StatisticTag, Statistic> dailyStatistics = new LinkedHashMap<>();
    /**
     * Represents player weekly statistics
     */
    @Getter private LinkedHashMap<StatisticTag, Statistic> weeklyStatistics = new LinkedHashMap<>();
    /**
     * Represents player montly statistics
     */
    @Getter private LinkedHashMap<StatisticTag, Statistic> montlyStatistics = new LinkedHashMap<>();
    /**
     * Represents player full statistics
     */
    @Getter private LinkedHashMap<StatisticTag, Statistic> fullyStatistics = new LinkedHashMap<>();
    /**
     * Represents account settings and preferences.
     */
    @Getter private LinkedHashMap<SettingTag, Setting> settings = new LinkedHashMap<>();

    public DataHandler(UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
        checkForPendingDatas();
    }

    public void checkForPendingDatas() {
        // SETTINGS
        registerSetting(SettingTag.CHAT_MESSAGES, new Setting(true));
        registerSetting(SettingTag.PRIVATE_MESSAGES, new Setting(true));
        registerSetting(SettingTag.ALERT_MESSAGES, new Setting(true));
        registerSetting(SettingTag.PARTY_INVITATIONS, new Setting(true));
        registerSetting(SettingTag.PARTY_MESSAGES, new Setting(true));
        registerSetting(SettingTag.STAFF_MESSAGES, new Setting(true));
        registerSetting(SettingTag.STAFF_CHAT_MODE, new Setting(false));
        registerSetting(SettingTag.CLAN_INVITATIONS, new Setting(true));
        registerSetting(SettingTag.CLAN_MESSAGES, new Setting(true));
        registerSetting(SettingTag.CLAN_CHAT_MODE, new Setting(false));
        registerSetting(SettingTag.FRIEND_INVITATIONS, new Setting(true));
        registerSetting(SettingTag.HIDE_STATISTICS, new Setting(false));
        registerSetting(SettingTag.AUTO_JOIN_VANISHED, new Setting(false));
        registerSetting(SettingTag.HIDE_LOBBY_PLAYERS, new Setting(false));
        registerSetting(SettingTag.ACCOUNT_PASSWORD, new Setting(""));
        registerSetting(SettingTag.NICKNAME, new Setting(""));
        registerSetting(SettingTag.BUILDING, new Setting(false));
        registerSetting(SettingTag.REEMDEMED_TRIAL_VIP, new Setting(false));
        registerSetting(SettingTag.LAST_IP_ADDRESS, new Setting(""));
        registerSetting(SettingTag.FIRST_LOGGED_IN, new Setting(Long.valueOf(0)));
        registerSetting(SettingTag.LAST_LOGGED_IN, new Setting(Long.valueOf(0)));
        registerSetting(SettingTag.COINS, new Setting(0));
        registerSetting(SettingTag.TWITTER, new Setting(""));
        registerSetting(SettingTag.YOUTUBE_CHANNEL, new Setting(""));
        registerSetting(SettingTag.INSTAGRAM, new Setting(""));
        registerSetting(SettingTag.TWITCH, new Setting(""));
        registerSetting(SettingTag.TIKTOK, new Setting(""));
        registerSetting(SettingTag.DISCORD, new Setting(""));

        // STATISTICS

        // PUNISHMENTS
        registerStatsTag(StatisticTag.REGISTERED_BAN_PUNISHMENTS, 0, false);
        registerStatsTag(StatisticTag.REGISTERED_SILENT_PUNISHMENTS, 0, false);

        // PVP
        registerStatsTag(StatisticTag.PVP_RATING, 0, false);

        registerStatsTag(StatisticTag.PVP_ARENA_KILLS, 0, false);
        registerStatsTag(StatisticTag.PVP_ARENA_DEATHS, 0, true);
        registerStatsTag(StatisticTag.PVP_ARENA_KS, 0, false);
        registerStatsTag(StatisticTag.PVP_ARENA_GREATER_KS, 0, false);

        registerStatsTag(StatisticTag.PVP_FPS_KILLS, 0, false);
        registerStatsTag(StatisticTag.PVP_FPS_DEATHS, 0, true);
        registerStatsTag(StatisticTag.PVP_FPS_KS, 0, false);
        registerStatsTag(StatisticTag.PVP_FPS_GREATER_KS, 0, false);

        // DUELS
        registerStatsTag(StatisticTag.DUELS_SOUP_RATING, 0, false);
        registerStatsTag(StatisticTag.DUELS_SOUP_MATCHES, 0, true);

        registerStatsTag(StatisticTag.DUELS_SOUP_1V1_WINS, 0, false);
        registerStatsTag(StatisticTag.DUELS_SOUP_1V1_LOSSES, 0, true);
        registerStatsTag(StatisticTag.DUELS_SOUP_1V1_KS, 0, false);
        registerStatsTag(StatisticTag.DUELS_SOUP_1V1_GREATER_KS, 0, false);

        registerStatsTag(StatisticTag.DUELS_SOUP_2V2_WINS, 0, false);
        registerStatsTag(StatisticTag.DUELS_SOUP_2V2_LOSSES, 0, true);
        registerStatsTag(StatisticTag.DUELS_SOUP_2V2_KS, 0, false);
        registerStatsTag(StatisticTag.DUELS_SOUP_2V2_GREATER_KS, 0, false);

        registerStatsTag(StatisticTag.DUELS_GLAD_RATING, 0, false);
        registerStatsTag(StatisticTag.DUELS_GLAD_MATCHES, 0, true);

        registerStatsTag(StatisticTag.DUELS_GLAD_1V1_WINS, 0, false);
        registerStatsTag(StatisticTag.DUELS_GLAD_1V1_LOSSES, 0, true);
        registerStatsTag(StatisticTag.DUELS_GLAD_1V1_KS, 0, false);
        registerStatsTag(StatisticTag.DUELS_GLAD_1V1_GREATER_KS, 0, false);

        registerStatsTag(StatisticTag.DUELS_GLAD_2V2_WINS, 0, false);
        registerStatsTag(StatisticTag.DUELS_GLAD_2V2_LOSSES, 0, true);
        registerStatsTag(StatisticTag.DUELS_GLAD_2V2_KS, 0, false);
        registerStatsTag(StatisticTag.DUELS_GLAD_2V2_GREATER_KS, 0, false);

        // HG
        registerStatsTag(StatisticTag.HG_RATING, 0, false);
        registerStatsTag(StatisticTag.HG_SINGLEKIT_WINS, 0, false);
        registerStatsTag(StatisticTag.HG_MULTIKIT_WINS, 0, false);
        registerStatsTag(StatisticTag.HG_SINGLEKIT_KILLS, 0, false);
        registerStatsTag(StatisticTag.HG_MULTIKIT_KILLS, 0, false);
        registerStatsTag(StatisticTag.HG_SINGLEKIT_DEATHS, 0, true);
        registerStatsTag(StatisticTag.HG_MULTIKIT_DEATHS, 0, true);
        registerStatsTag(StatisticTag.HG_SINGLEKIT_ASSISTS, 0, true);
        registerStatsTag(StatisticTag.HG_MULTIKIT_ASSISTS, 0, true);
    }

    public Statistic getStatistic(StatisticTag tag) {
        return getStatistic(tag, StatisticType.FULL);
    }

    public Statistic getStatistic(StatisticTag tag, StatisticType type) {
        switch (type) {
            case DAILY: {
                return dailyStatistics.get(tag);
            }
            case WEEKLY: {
                return weeklyStatistics.get(tag);
            }
            case MONTLY: {
                return montlyStatistics.get(tag);
            }
            case FULL: {
                return fullyStatistics.get(tag);
            }
        }
        return null;
    }

    public void resetStatistics(StatisticType... types) {
        if (types == null)
            return;
        for (StatisticType type : types) {
            switch (type) {
                case DAILY: {
                    dailyStatistics.values().forEach(statistic -> statistic.setValue(0));
                    break;
                }
                case WEEKLY: {
                    weeklyStatistics.values().forEach(statistic -> statistic.setValue(0));
                    break;
                }
                case MONTLY: {
                    montlyStatistics.values().forEach(statistic -> statistic.setValue(0));
                    break;
                }
                case FULL: {
                    fullyStatistics.values().forEach(statistic -> statistic.setValue(0));
                    break;
                }
            }
        }
    }

    public void addStatistic(StatisticTag tag, int i) {
        changeStatistic(tag, stats -> stats.add(i));
    }

    public void removeStatistic(StatisticTag tag, int i) {
        changeStatistic(tag, stats -> stats.subtract(i));
    }

    public void changeStatistic(StatisticTag tag, Consumer<Statistic> consumer) {
        Statistic daily = dailyStatistics.get(tag);
        if (daily != null)
            consumer.accept(daily);

        Statistic weekly = weeklyStatistics.get(tag);
        if (weekly != null)
            consumer.accept(weekly);

        Statistic montly = montlyStatistics.get(tag);
        if (montly != null)
            consumer.accept(montly);

        Statistic full = fullyStatistics.get(tag);
        if (full != null)
            consumer.accept(full);
    }

    public void registerSetting(SettingTag tag, Setting setting) {
        settings.computeIfAbsent(tag, v -> setting);
    }

    public void registerStatsTag(StatisticTag tag, int defValue, boolean fullOnly) {
        fullyStatistics.computeIfAbsent(tag, v -> new Statistic(defValue));
        if (!fullOnly) {
            dailyStatistics.computeIfAbsent(tag, v -> new Statistic(defValue));
            weeklyStatistics.computeIfAbsent(tag, v -> new Statistic(defValue));
            montlyStatistics.computeIfAbsent(tag, v -> new Statistic(defValue));
        }
    }
}
