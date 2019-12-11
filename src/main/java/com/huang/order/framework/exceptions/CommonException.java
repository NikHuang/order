package com.huang.order.framework.exceptions;

import com.huang.order.vo.CodeMessage;
import lombok.Data;

/**
 * Created by Administrator on 2019/12/11.
 */
@Data
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -1481592824748067267L;

    private CodeMessage codeMessage;

    public CommonException(CodeMessage codeMessage) {
        super(codeMessage.toString());
        this.codeMessage = codeMessage;
    }
}
