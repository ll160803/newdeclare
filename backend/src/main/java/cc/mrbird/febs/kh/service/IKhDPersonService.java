package cc.mrbird.febs.kh.service;

import cc.mrbird.febs.kh.entity.KhDPerson;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cc.mrbird.febs.common.domain.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 被打分人 服务类
 * </p>
 *
 * @author viki
 * @since 2022-05-12
 */
public interface IKhDPersonService extends IService<KhDPerson> {

        IPage<KhDPerson> findKhDPersons(QueryRequest request, KhDPerson khDPerson);

        IPage<KhDPerson> findKhDPersonList(QueryRequest request, KhDPerson khDPerson);

        void createKhDPerson(KhDPerson khDPerson);

        void updateKhDPerson(KhDPerson khDPerson);

        void deleteKhDPersons(String[]Ids);

        List<KhDPerson> getAll(String userAccount,String dcaYear);
        }
