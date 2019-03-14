package com.lucky.admin.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDICT is a Querydsl query type for DICT
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDICT extends EntityPathBase<DICT> {

    private static final long serialVersionUID = -390632207L;

    public static final QDICT dICT = new QDICT("dICT");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    public final StringPath dictCode = createString("dictCode");

    public final StringPath dictDataCode = createString("dictDataCode");

    public final StringPath dictDataText = createString("dictDataText");

    public final StringPath dictName = createString("dictName");

    public final NumberPath<Integer> dispOrder = createNumber("dispOrder", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final NumberPath<Integer> useFlag = createNumber("useFlag", Integer.class);

    public QDICT(String variable) {
        super(DICT.class, forVariable(variable));
    }

    public QDICT(Path<? extends DICT> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDICT(PathMetadata metadata) {
        super(DICT.class, metadata);
    }

}

