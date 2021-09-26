package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaUserYj;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 学术业绩用户表 服务类
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
public interface
IDcaUserYjService extends IService<DcaUserYj> {

        IPage<DcaUserYj> findDcaUserYjs(QueryRequest request, DcaUserYj dcaUserYj);

        IPage<DcaUserYj> findDcaUserYjList(QueryRequest request, DcaUserYj dcaUserYj);

        void createDcaUserYj(DcaUserYj dcaUserYj);

        void updateDcaUserYj(DcaUserYj dcaUserYj);

        void deleteDcaUserYjs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        void deleteByuserid(String userId,String year);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        List<DcaUserYj> getMudulesByUserId(String userId,String year,String dj);
        }
