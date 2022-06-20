package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhBCopySciencesearch;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 科研项目 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface IKhBCopySciencesearchService extends IService<KhBCopySciencesearch> {

        IPage<KhBCopySciencesearch> findKhBCopySciencesearchs(QueryRequest request, KhBCopySciencesearch khBCopySciencesearch);

        IPage<KhBCopySciencesearch> findKhBCopySciencesearchList(QueryRequest request, KhBCopySciencesearch khBCopySciencesearch);

        void createKhBCopySciencesearch(KhBCopySciencesearch khBCopySciencesearch);

        void updateKhBCopySciencesearch(KhBCopySciencesearch khBCopySciencesearch);

        void deleteKhBCopySciencesearchs(String[]Ids);

        List<KhBCopySciencesearch> getAll(String userAccount,String dcaYear);
        }
