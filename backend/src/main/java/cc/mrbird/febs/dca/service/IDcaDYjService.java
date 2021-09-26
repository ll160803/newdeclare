package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaDYj;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 学术业绩 服务类
 * </p>
 *
 * @author viki
 * @since 2021-09-23
 */
public interface IDcaDYjService extends IService<DcaDYj> {

        IPage<DcaDYj> findDcaDYjs(QueryRequest request, DcaDYj dcaDYj);

        IPage<DcaDYj> findDcaDYjList(QueryRequest request, DcaDYj dcaDYj);

        void createDcaDYj(DcaDYj dcaDYj);

        void updateDcaDYj(DcaDYj dcaDYj);

        void deleteDcaDYjs(String[]Ids);

        void deleteByuseraccount(String userAccount);

        int getMaxDisplayIndexByuseraccount(String userAccount);
        /**
         * 模块树
         * @return
         */
        Map<String, Object> findDepts(String dj);

        /**
         * 根据用户ID获取模块
         * @param userId
         * @return
         */
        Map<String, Object> findDeptsByUserId(String userId,String year);
        }
