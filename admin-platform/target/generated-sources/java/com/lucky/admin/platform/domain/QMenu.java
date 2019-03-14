package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -390335814L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final NumberPath<Integer> dispOrder = createNumber("dispOrder", Integer.class);

    public final StringPath icon = createString("icon");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath jump = createString("jump");

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final ListPath<Menu, QMenu> list = this.<Menu, QMenu>createList("list", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final QMenu parentMenu;

    public final SetPath<PlatformRole, QPlatformRole> roleSet = this.<PlatformRole, QPlatformRole>createSet("roleSet", PlatformRole.class, QPlatformRole.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentMenu = inits.isInitialized("parentMenu") ? new QMenu(forProperty("parentMenu"), inits.get("parentMenu")) : null;
    }

}

