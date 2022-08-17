package cc.mrbird.febs.zq.service;

import cc.mrbird.febs.zq.entity.ZqBCopySciencesearch;
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
 * @since 2022-06-24
 */
public interface IZqBCopySciencesearchService extends IService<ZqBCopySciencesearch> {

        IPage<ZqBCopySciencesearch> findZqBCopySciencesearchs(QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch);

        IPage<ZqBCopySciencesearch> findZqBCopySciencesearchList(QueryRequest request, ZqBCopySciencesearch zqBCopySciencesearch);

        void createZqBCopySciencesearch(ZqBCopySciencesearch zqBCopySciencesearch);

        void updateZqBCopySciencesearch(ZqBCopySciencesearch zqBCopySciencesearch);

        void deleteZqBCopySciencesearchs(String[]Ids);

        List<ZqBCopySciencesearch> getAll(String userAccount,String dcaYear);
        }
