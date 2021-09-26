package org.shield.admin.mapper;

import org.apache.ibatis.annotations.Select;
import org.shield.admin.model.AppRelease;
import org.shield.mybatis.mapper.Mapper;

/**
 * AppRelease Mapper
 *
 * @author zacksleo@gmail.com
 */
public interface AppReleaseMapper extends Mapper<AppRelease> {

    /**
     * find latest by catalog
     *
     * @param catalog
     * @return
     */
    @Select("SELECT * from app_release where catalog=#{catalog} AND is_active=1 AND is_deleted=0 ORDER BY version_num DESC LIMIT 1")
    AppRelease findLatestByCatalog(Integer catalog);
}
