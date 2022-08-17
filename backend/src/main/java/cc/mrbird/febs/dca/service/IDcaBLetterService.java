package cc.mrbird.febs.dca.service;

import cc.mrbird.febs.dca.entity.DcaBLetter;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 通讯评审 服务类
 * </p>
 *
 * @author viki
 * @since 2022-07-26
 */
public interface IDcaBLetterService extends IService<DcaBLetter> {

        IPage<DcaBLetter> findDcaBLetters(QueryRequest request, DcaBLetter dcaBLetter);

        IPage<DcaBLetter> findDcaBLetterList(QueryRequest request, DcaBLetter dcaBLetter);

        void createDcaBLetter(DcaBLetter dcaBLetter);

        void updateDcaBLetter(DcaBLetter dcaBLetter);

        void deleteDcaBLetters(String[]Ids);

        List<DcaBLetter> getAll(String userAccount,String dcaYear);
        }
