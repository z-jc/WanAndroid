package com.dq.util.http;

import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;

/**
 * FileName: ResponseParser
 * Author: admin
 * Date: 2020/5/14 16:47
 * Description:对象解析器
 */
@Parser(name = "Response")
public class RxResponseParser<T> extends AbstractParser<T> {

    protected RxResponseParser() {
        super();
    }

    public RxResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        final Type type = ParameterizedTypeImpl.get(RxBaseResponse.class, mType); //获取泛型类型
        RxBaseResponse<T> data = convert(response, type);
        T t = data.getData(); //获取data字段
        if (data.getCode() != 200 || t == null) {//这里假设code不等于200，代表数据不正确，抛出异常
            throw new ParseException(String.valueOf(data.getCode()), data.getMsg(), response);
        }
        return t;
    }
}