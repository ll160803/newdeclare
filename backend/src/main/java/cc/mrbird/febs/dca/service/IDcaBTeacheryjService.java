package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBTeacheryj;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 主要教学业绩 服务类
 * </p>
 *
 * @author viki
 * @since 2021-09-14
 */
public interface IDcaBTeacheryjService extends IService<DcaBTeacheryj> {

        IPage<DcaBTeacheryj> findDcaBTeacheryjs(QueryRequest request, DcaBTeacheryj dcaBTeacheryj);

        IPage<DcaBTeacheryj> findDcaBTeacheryjList(QueryRequest request, DcaBTeacheryj dcaBTeacheryj);

        void createDcaBTeacheryj(DcaBTeacheryj dcaBTeacheryj);

        void updateDcaBTeacheryj(DcaBTeacheryj dcaBTeacheryj);

        void deleteDcaBTeacheryjs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        }
