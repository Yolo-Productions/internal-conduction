package br.com.yolo.core.account.connection.statistic;

import br.com.yolo.core.account.connection.datahandler.DataHandler;

import java.util.Map;

public interface StatisticBoard {

    Map<StatisticTag, Statistic> getStatisticMap(DataHandler dataHandler);
}
