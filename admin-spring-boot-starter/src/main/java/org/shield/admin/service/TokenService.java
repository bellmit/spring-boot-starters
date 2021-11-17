package org.shield.admin.service;

import javax.validation.Valid;
import org.shield.admin.form.PasswordLoginForm;
import org.shield.admin.form.SmsLoginForm;
import org.shield.admin.vo.TokenVo;
import org.springframework.validation.annotation.Validated;

/**
 * @author zacksleo@gmail.com
 */
@Validated
public interface TokenService {

    /**
     * 创建 Token
     *
     * @param form
     * @return
     * @throws Exception
     */
    TokenVo create(PasswordLoginForm form) throws Exception;

    /**
     * 创建 Token
     *
     * @param form
     * @return
     * @throws Exception
     */
    TokenVo create(@Valid SmsLoginForm form) throws Exception;
}
