package com.lxp.user.application.port.required;

import com.lxp.user.application.port.required.query.TagResult;

import java.util.List;

@FunctionalInterface
public interface TagQueryPort {

    List<TagResult> findTags(List<Long> id);

}
