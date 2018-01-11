package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.h9.api.model.dto.StickCommentDTO;
import com.h9.api.model.dto.StickDto;
import com.h9.api.model.vo.HomeVO;
import com.h9.api.model.vo.StickCommentSimpleVO;
import com.h9.api.model.vo.StickCommentVO;
import com.h9.api.model.vo.StickRewardVO;
import com.h9.api.model.vo.StickSearchVO;
import com.h9.api.model.vo.StickDetailVO;
import com.h9.api.model.vo.StickSampleVO;
import com.h9.api.model.vo.StickSearchVO;
import com.h9.api.model.vo.StickTypeDetailVo;
import com.h9.api.model.vo.StickTypeVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.community.StickCommentLike;
import com.h9.common.db.entity.community.StickLike;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.config.Banner;
import com.h9.common.db.entity.hotel.Hotel;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserExtends;
import com.h9.common.db.repo.BannerRepository;
import com.h9.common.db.repo.StickCommentLikeRepository;
import com.h9.common.db.repo.StickCommentRepository;
import com.h9.common.db.repo.StickLikeRepository;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickTypeRepository;
import com.h9.common.db.repo.UserExtendsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.modle.vo.Config;
import com.h9.common.utils.DateUtil;
import lombok.extern.jbosslog.JBossLog;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.h9.common.constant.ParamConstant.JIUYUAN_ICON;

/**
 * Created with IntelliJ IDEA.
 * Description:TODO
 * StickService:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/2
 * Time: 14:49
 */
@Component
public class StickService {

    Logger logger = Logger.getLogger(StickService.class);

    @Resource
    private StickTypeRepository stickTypeRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CommonService commonService;
    @Resource
    private StickRepository stickRepository;
    @Resource
    private BannerRepository bannerRepository;
    @Resource
    private StickCommentRepository stickCommentRepository;
    @Resource
    private StickLikeRepository stickLikeRepository;
    @Resource
    private StickCommentLikeRepository stickCommentLikeRepository;
    @Resource
    private UserExtendsRepository userExtendsRepository;
    @Resource
    private ConfigService configService;

    public Result getStickType(){
        List<StickType> stickTypes = stickTypeRepository.findAll();
        List<StickTypeVO> stickTypeVOS = new ArrayList<>();
        if(stickTypes !=null){
            stickTypeVOS = stickTypes.stream().map(StickTypeVO::new).collect(Collectors.toList());
        }
        return Result.success(stickTypeVOS);
    }

    @Transactional
    public Result addStick(Long userId, StickDto stickDto){
        User user = userRepository.findOne(userId);
        StickType stickType = stickTypeRepository.findOne(stickDto.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        Stick stick = new Stick();
        stick.setTitle(stickDto.getTitle());
        stick.setContent(stickDto.getContent());
        stick.setStickType(stickType);
        stick.setUser(user);
        double latitude = stickDto.getLatitude();
        double longitude = stickDto.getLongitude();
        stick.setLatitude(stickDto.getLatitude());
        stick.setLongitude(longitude);
        if(latitude!=0&&longitude!=0){
            CommonService.AddressResult addressDetail = commonService.getAddressDetail(latitude, longitude);
            if(addressDetail!=null){
                stick.setAddress(addressDetail.getDetailAddress());
                stick.setCity(addressDetail.getCity());
                stick.setProvince(addressDetail.getProvince());
                stick.setDistrict(addressDetail.getDistrict());
            }
        }
        Stick stickFromDb = stickRepository.save(stick);
        return Result.success(new StickSampleVO(stickFromDb));
    }


    public Result listStick(String type,int page,Integer limit){

        if(type.equals("config_home")){
            Page<Stick> home = stickRepository.find4Home(stickRepository.pageRequest(page,limit!=null?limit:5));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }else if(type.equals("config_hot")){
            Page<Stick> home = stickRepository.find4Hot(stickRepository.pageRequest(page,limit!=null?limit:20));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }else{
            Page<Stick> home = stickRepository.findType(type,stickRepository.pageRequest(page,limit!=null?limit:20));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }
    }

    @Transactional
    public Result home(){
        return Result.success(getBanner(3));
    }

    public Result typeDetail(long typeId){
        StickType stickType = stickTypeRepository.findOne(typeId);
        if(stickType==null){
            return Result.fail("分类不存在");
        }
        return Result.success(new StickTypeDetailVo(stickType));
    }

    /**
     * 获取帖子详情
     */
    @Transactional
    public Result detail(long id) {
        Stick stick = stickRepository.findOne(id);
        if (stick == null) {
            return Result.fail("帖子不存在");
        }
        //StickComment stickComment = stickCommentRepository.find
        StickDetailVO stickDetailVO = new StickDetailVO(stick);
        stickDetailVO.setListMap(getBanner(4));
        return Result.success(stickDetailVO);
    }


    /**
     * 拿到广告
     * @param location 3 社区首页广告  4 帖子详情广告
     * @return map
     */
     private Map<String, List<HomeVO>> getBanner(Integer location){
        Map<String, List<HomeVO>> listMap = new HashMap<>();
        try(Stream<Banner> activiBanner = bannerRepository.findActiviBanner(location, new Date())){
            Function<Banner, String> function = b -> b.getBannerType().getCode();
            listMap = activiBanner.collect(Collectors.groupingBy(function, Collectors.mapping(HomeVO::new, Collectors.toList())));
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
        }
        return listMap;
    }

    /**
     * 搜索帖子
     * @param str  匹配字符串
     * @param page 页码
     *@param limit @return Result
     */
    public Result search(String str, Integer page, Integer limit) {
        if (str == null){
            return  Result.fail("请输入搜索内容");
        }
        PageRequest pageRequest = stickRepository.pageRequest(page, limit);
        Page<Stick> stickPage = stickRepository.findStickList("%"+str+"%",pageRequest);
        if (CollectionUtils.isNotEmpty(stickPage.getContent())) {
            PageResult<StickSearchVO> pageResult = new PageResult<>(stickPage).result2Result(StickSearchVO::new);
            return Result.success(pageResult);
        } else {
            return Result.fail("没有找到此类帖子");
        }
    }


    /**
     * 点赞贴子或评论
     * @param userId 用户id
     * @param id 贴子或评论id
     * @param type 点赞类型
     * @return Result
     */
    public Result like( long userId,long id, Integer type) {
        /* 点赞贴子*/
        if (type == 1){
            Stick stick = stickRepository.findOne(id);
            if (stick == null){
                return Result.fail("点赞失败,贴子不存在");
            }
            // 判断该用户是否为该贴点过赞
            StickLike stickLike = stickLikeRepository.findByUserIdAndStickId(userId,id);
            if (stickLike == null){
                StickLike stickLikeNew = new StickLike();
                stickLikeNew.setStatus(1);
                stickLikeNew.setStickId(id);
                stickLikeNew.setUserId(userId);
                stickLikeRepository.save(stickLikeNew);
            }else if (stickLike.getStatus()!= 1){
                stickLike.setStatus(1);
                stickLikeRepository.save(stickLike);
            }else{
                return Result.fail("您已经点过赞啦");
            }
            // 贴子点赞数加一
            stick.setLikeCount(stick.getLikeCount()+1);
            stickRepository.save(stick);
            return Result.success("点赞成功");
        }

        /* 点赞评论*/
        else if (type == 2){
            StickComment stickComment = stickCommentRepository.findOne(id);
            if (stickComment == null){
                return Result.fail("点赞失败,该评论不存在或已被删除");
            }
            // 判断该用户是否为该评论点过赞
            StickCommentLike stickCommentLike = stickCommentLikeRepository.findByUserIdAndStickCommentId(userId,id);
            if (stickCommentLike == null){
                StickCommentLike stickCommentLikeNew = new StickCommentLike();
                stickCommentLikeNew.setUserId(userId);
                stickCommentLikeNew.setStickCommentId(id);
                stickCommentLikeNew.setStatus(1);
                stickCommentLikeRepository.save(stickCommentLikeNew);
            }else if (stickCommentLike.getStatus()!= 1){
                stickCommentLike.setStatus(1);
                stickCommentLikeRepository.save(stickCommentLike);
            }else{
                return Result.fail("您已经点过赞啦");
            }
            // 评论点赞数加一
            stickComment.setLikeCount(stickComment.getLikeCount()+1);
            stickCommentRepository.save(stickComment);
            return Result.success("点赞成功");
        }

        /* 点赞类型不存在*/
        return Result.fail("点赞失败");
    }

    /**
     * 添加贴子或评论回复
     * @param userId 用户id
     * @param stickCommentDTO 请求对象
     * @return Result
     */
    public Result addComment(long userId, StickCommentDTO stickCommentDTO) {
        // 贴子id
        Stick stick = stickRepository.findOne(stickCommentDTO.getStickId());
        if (stick == null){
            return Result.fail("贴子不存在或已被删除");
        }
        StickComment stickComment = new StickComment();
        // 回复的用户
        User user = userRepository.findOne(userId);
        stickComment.setAnswerUser(user);
        // 回复内容
        stickComment.setContent(stickCommentDTO.getContent());
        // 回复级别
        stickComment.setLevel(stickCommentDTO.getLevel());
        // 回复楼层
        stickComment.setFloor(stickCommentDTO.getFloor());
        // @的用户
        Long notifyUserId = stickCommentDTO.getNotifyUserId();
        if (notifyUserId != null){
            User aitUser = userRepository.findOne(notifyUserId);
            if (aitUser != null){
                stickComment.setNotifyUserId(aitUser);
            }
        }
        // 父级id
        Long stickCommentId = stickCommentDTO.getStickCommentId();
        if (stickCommentId != null){
            StickComment stickCommentPid = stickCommentRepository.findById(stickCommentId);
            if(stickCommentPid!=null){
                stickComment.setStickComment(stickCommentPid);
            }else {
                return Result.fail("该楼层不存在或已被删除");
            }
        }
        // 贴子id
        stickComment.setStick(stick);
        stickCommentRepository.save(stickComment);
        // 增加阅读数和回复数
        stick.setAnswerCount(stick.getAnswerCount()+1);
        stick.setReadCount(stick.getReadCount()+1);
        stickRepository.save(stick);
        return Result.success("回复成功");
    }


    /**
     * 拿到评论
     */
    public Result getComment(long stickId, Integer page, Integer limit) {
        PageResult<StickComment> pageResult = stickCommentRepository.findStickCommentList(stickId,page, limit);
        if (pageResult == null){
            return Result.success("暂无评论");
        }
        return Result.success(pageResult.result2Result(this::stickComent2Vo));
    }


    public StickCommentVO stickComent2Vo(StickComment stickComment){
        User user = stickComment.getAnswerUser();
        if (user.getId() == null){
            return new StickCommentVO();
        }
        UserExtends userExtends = userExtendsRepository.findByUserId(user.getId());
        Integer  sex = userExtends.getSex();

        // 拿到回复的回复列表
        List<StickCommentSimpleVO> stickCommentSimpleVOS = new ArrayList<>();
            long stickCommentParentId = stickComment.getId();
            List<StickComment> stickCommentChild= stickCommentRepository.findByBackId(stickCommentParentId);
            if (CollectionUtils.isNotEmpty(stickCommentChild)){
                stickCommentSimpleVOS = stickCommentChild.stream().map(StickCommentSimpleVO::new).collect(Collectors.toList());
        }
        return new StickCommentVO(sex, stickComment,stickCommentSimpleVOS);
    }

    /**
     * 赞赏列表
     */
    public Result getReward(long stickId) {
        List<Config> mapListConfig = configService.getMapListConfig(ParamConstant.REWARD_MONEY);
        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        Stick stick = stickRepository.findOne(stickId);
        StickRewardVO stickRewardVO = new StickRewardVO(stick,mapListConfig);
        return Result.success(stickRewardVO);
    }

    /**
     * 赞赏酒元
     */
    /*public Result rewardJiuyuan(long stickId, Integer money) {
        String icon = configService.getStringConfig(JIUYUAN_ICON);

    }*/
}
