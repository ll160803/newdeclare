package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhBCopyScientificprize;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 自任职以来科研获奖情况 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-13
 */
public interface IKhBCopyScientificprizeService extends IService<KhBCopyScientificprize> {

        IPage<KhBCopyScientificprize> findKhBCopyScientificprizes(QueryRequest request, KhBCopyScientificprize khBCopyScientificprize);

        IPage<KhBCopyScientificprize> findKhBCopyScientificprizeList(QueryRequest request, KhBCopyScientificprize khBCopyScientificprize);

        void createKhBCopyScientificprize(KhBCopyScientificprize khBCopyScientificprize);

        void updateKhBCopyScientificprize(KhBCopyScientificprize khBCopyScientificprize);

        void deleteKhBCopyScientificprizes(String[]Ids);

        List<KhBCopyScientificprize> getAll(String userAccount,String dcaYear);
        }
