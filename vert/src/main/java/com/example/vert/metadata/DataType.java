package com.example.vert.metadata;

import java.util.Map;

/**
 * 物模型数据类型
 *
 * @author zhouhao
 * @since 1.0.0
 */
public interface DataType extends Metadata, FormatSupport {

    /**
     * 验证是否合法
     *
     * @param value 值
     * @return ValidateResult
     */
    ValidateResult validate(Object value);

    /**
     * @return 类型标识
     */
    default String getType() {
        return getId();
    }

    /**
     * @return 拓展属性
     */
    @Override
    default Map<String, Object> getExpands() {
        return null;
    }

}
