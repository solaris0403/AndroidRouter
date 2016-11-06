package com.tony.router.matcher;

import java.util.Set;

/**
 * 匹配器操作类
 */
public interface IMatcherHandler {
    Set<String> getMatchSchemes();

    void setMatchSchemes(String... schemes);

    void addMatchSchemes(String scheme);

    void removeMatchSchemes(String scheme);
}
