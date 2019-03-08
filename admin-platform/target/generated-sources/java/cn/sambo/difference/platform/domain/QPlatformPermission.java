package cn.sambo.difference.platform.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlatformPermission is a Querydsl query type for PlatformPermission
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlatformPermission extends EntityPathBase<PlatformPermission> {

    private static final long serialVersionUID = -2129643331L;

    public static final QPlatformPermission platformPermission = new QPlatformPermission("platformPermission");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.sql.Timestamp> createdTime = _super.createdTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.sql.Timestamp> lastUpdateTime = _super.lastUpdateTime;

    public final StringPath name = createString("name");

    public final SetPath<PlatformRole, QPlatformRole> platformRoles = this.<PlatformRole, QPlatformRole>createSet("platformRoles", PlatformRole.class, QPlatformRole.class, PathInits.DIRECT2);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public final StringPath url = createString("url");

    public QPlatformPermission(String variable) {
        super(PlatformPermission.class, forVariable(variable));
    }

    public QPlatformPermission(Path<? extends PlatformPermission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlatformPermission(PathMetadata metadata) {
        super(PlatformPermission.class, metadata);
    }

}

