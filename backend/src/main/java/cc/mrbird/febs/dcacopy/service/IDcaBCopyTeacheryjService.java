package cc.mrbird.febs.dcacopy.service;

import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacheryj;
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
 * @since 2021-09-30
 */
public interface IDcaBCopyTeacheryjService extends IService<DcaBCopyTeacheryj> {

        IPage<DcaBCopyTeacheryj> findDcaBCopyTeacheryjs(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj);

        IPage<DcaBCopyTeacheryj> findDcaBCopyTeacheryjList(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj);

        void createDcaBCopyTeacheryj(DcaBCopyTeacheryj dcaBCopyTeacheryj);

        void updateDcaBCopyTeacheryj(DcaBCopyTeacheryj dcaBCopyTeacheryj);

        void deleteDcaBCopyTeacheryjs(String[]Ids);

        List<DcaBCopyTeacheryj> getAll(String userAccount,String dcaYear, String gwDj);
        }
