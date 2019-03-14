package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlatformRole is a Querydsl query type for PlatformRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformRole extends EntityPathBase<PlatformRole> {

    private static final long serialVersionUID = -1309393436L;

    public static final QPlatformRole platformRole = new QPlatformRole("platformRole");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final SetPath<PlatformPermission, QPlatformPermission> platformPermissions = this.<PlatformPermission, QPlatformPermission>createSet("platformPermissions", PlatformPermission.class, QPlatformPermission.class, PathInits.DIRECT2);

    public final SetPath<PlatformUser, QPlatformUser> platformUsers = this.<PlatformUser, QPlatformUser>createSet("platformUsers", PlatformUser.class, QPlatformUser.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final StringPath roleDesc = createString("roleDesc");

    public final StringPath roleName = createString("roleName");

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QPlatformRole(String variable) {
        super(PlatformRole.class, forVariable(variable));
    }

    public QPlatformRole(Path<? extends PlatformRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlatformRole(PathMetadata metadata) {
        super(PlatformRole.class, metadata);
    }

}

