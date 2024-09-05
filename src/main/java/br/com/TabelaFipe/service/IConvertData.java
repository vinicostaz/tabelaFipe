package br.com.TabelaFipe.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
