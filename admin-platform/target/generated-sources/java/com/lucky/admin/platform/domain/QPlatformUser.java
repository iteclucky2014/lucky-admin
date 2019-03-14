package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlatformUser is a Querydsl query type for PlatformUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformUser extends EntityPathBase<PlatformUser> {

    private static final long serialVersionUID = -1309300423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlatformUser platformUser = new QPlatformUser("platformUser");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final SetPath<PlatformRole, QPlatformRole> authorities = this.<PlatformRole, QPlatformRole>createSet("authorities", PlatformRole.class, QPlatformRole.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final QPlatformDept dept;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final StringPath mail = createString("mail");

    public final StringPath password = createString("password");

    public final StringPath realName = createString("realName");

    public final StringPath tel = createString("tel");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final StringPath url = createString("url");

    public final StringPath username = createString("username");

    public QPlatformUser(String variable) {
        this(PlatformUser.class, forVariable(variable), INITS);
    }

    public QPlatformUser(Path<? extends PlatformUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlatformUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlatformUser(PathMetadata metadata, PathInits inits) {
        this(PlatformUser.class, metadata, inits);
    }

    public QPlatformUser(Class<? extends PlatformUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dept = inits.isInitialized("dept") ? new QPlatformDept(forProperty("dept"), inits.get("dept")) : null;
    }

}

