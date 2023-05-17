package br.com.yolo.core.player.account.statistic;

import br.com.yolo.core.player.account.datahandler.DataHandler;

import java.util.Map;

public interface StatisticBoard {

    Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler);
}
