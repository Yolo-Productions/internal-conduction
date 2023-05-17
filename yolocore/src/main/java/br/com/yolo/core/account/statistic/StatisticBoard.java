package br.com.yolo.core.account.statistic;

import br.com.yolo.core.account.datahandler.DataHandler;

import java.util.Map;

public interface StatisticBoard {

    Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler);
}
