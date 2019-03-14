package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlatformDept is a Querydsl query type for PlatformDept
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformDept extends EntityPathBase<PlatformDept> {

    private static final long serialVersionUID = -1309819981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlatformDept platformDept = new QPlatformDept("platformDept");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Double> acreage = createNumber("acreage", Double.class);

    public final StringPath centerPoint = createString("centerPoint");

    public final ListPath<PlatformDept, QPlatformDept> childrenDeptList = this.<PlatformDept, QPlatformDept>createList("childrenDeptList", PlatformDept.class, QPlatformDept.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final NumberPath<Integer> delFlag = createNumber("delFlag", Integer.class);

    public final StringPath deptId = createString("deptId");

    public final StringPath deptName = createString("deptName");

    public final StringPath deptRange = createString("deptRange");

    public final ListPath<PlatformUser, QPlatformUser> deptUserList = this.<PlatformUser, QPlatformUser>createList("deptUserList", PlatformUser.class, QPlatformUser.class, PathInits.DIRECT2);

    public final NumberPath<Integer> dispOrder = createNumber("dispOrder", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final QPlatformDept parentDept;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final NumberPath<Double> waterDemand = createNumber("waterDemand", Double.class);

    public final NumberPath<Integer> zoom = createNumber("zoom", Integer.class);

    public QPlatformDept(String variable) {
        this(PlatformDept.class, forVariable(variable), INITS);
    }

    public QPlatformDept(Path<? extends PlatformDept> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlatformDept(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlatformDept(PathMetadata metadata, PathInits inits) {
        this(PlatformDept.class, metadata, inits);
    }

    public QPlatformDept(Class<? extends PlatformDept> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentDept = inits.isInitialized("parentDept") ? new QPlatformDept(forProperty("parentDept"), inits.get("parentDept")) : null;
    }

}

