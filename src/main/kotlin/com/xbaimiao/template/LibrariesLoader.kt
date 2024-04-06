package com.xbaimiao.template

import com.xbaimiao.easylib.util.Dependency
import com.xbaimiao.easylib.util.DependencyList

@DependencyList(
    [
        Dependency(
            "com.zaxxer:HikariCP:4.0.3",
            "com.zaxxer.hikari.HikariConfig",
            format = true
        ),
        Dependency(
            "de.tr7zw:item-nbt-api:2.12.3",
            "de.tr7zw.changeme.nbtapi.NBTItem",
            format = true,
            repoUrl = "https://repo.codemc.org/repository/maven-public/"
        ),
    ]
)
object LibrariesLoader {


}
