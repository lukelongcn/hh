package com.h9.api.service;

import com.alibaba.fastjson.JSONObject;
import com.h9.api.model.dto.LocationDTO;
import com.h9.api.model.dto.ReportDTO;
import com.h9.api.model.dto.StickCommentDTO;
import com.h9.api.model.dto.StickDto;
import com.h9.api.model.dto.StickRewardJiuYuanDTO;
import com.h9.api.model.vo.HomeVO;
import com.h9.api.model.vo.community.StickAddressVO;
import com.h9.api.model.vo.community.StickCommentSimpleVO;
import com.h9.api.model.vo.community.StickCommentVO;
import com.h9.api.model.vo.community.StickRewardMoneyVO;
import com.h9.api.model.vo.community.StickRewardUser;
import com.h9.api.model.vo.community.StickRewardVO;
import com.h9.api.model.vo.community.StickSearchVO;
import com.h9.api.model.vo.community.StickDetailVO;
import com.h9.api.model.vo.community.StickSampleVO;
import com.h9.api.model.vo.community.StickTypeDetailVo;
import com.h9.api.model.vo.community.StickTypeVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.common.CommonService;
import com.h9.common.common.ConfigService;
import com.h9.common.common.ServiceException;
import com.h9.common.constant.ParamConstant;
import com.h9.common.db.entity.account.BalanceFlow;
import com.h9.common.db.entity.community.Stick;
import com.h9.common.db.entity.community.StickComment;
import com.h9.common.db.entity.community.StickCommentLike;
import com.h9.common.db.entity.community.StickLike;
import com.h9.common.db.entity.community.StickReport;
import com.h9.common.db.entity.community.StickReward;
import com.h9.common.db.entity.community.StickType;
import com.h9.common.db.entity.config.Banner;

import com.h9.common.db.entity.order.GoodsType;
import com.h9.common.db.entity.user.User;
import com.h9.common.db.entity.user.UserAccount;
import com.h9.common.db.entity.user.UserExtends;
import com.h9.common.db.repo.BannerRepository;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.h9.common.db.repo.StickCommentLikeRepository;
import com.h9.common.db.repo.StickCommentRepository;
import com.h9.common.db.repo.StickLikeRepository;
import com.h9.common.db.repo.StickReportRepository;
import com.h9.common.db.repo.StickRepository;
import com.h9.common.db.repo.StickRewardResitory;
import com.h9.common.db.repo.StickTypeRepository;
import com.h9.common.db.repo.UserAccountRepository;
import com.h9.common.db.repo.UserExtendsRepository;
import com.h9.common.db.repo.UserRepository;
import com.h9.common.modle.vo.Config;
import com.h9.common.utils.NetworkUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.ptg.MemAreaPtg;
import org.jboss.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.h9.common.constant.ParamConstant.JIUYUAN_ICON;
import static com.h9.common.db.entity.config.BannerType.LocaltionEnum.STICK_DETAIL;
import static com.h9.common.db.entity.config.BannerType.LocaltionEnum.STICK_HOME;

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
    @Resource
    private UserAccountRepository userAccountRepository;
    @Resource
    private GoodsTypeReposiroty goodsTypeReposiroty;
    @Resource
    private StickReportRepository stickReportRepository;
    @Resource
    private StickRewardResitory stickRewardResitory;

    public Result getStickType(){
        List<StickType> stickTypes = stickTypeRepository.findAllTypeList();
        List<StickTypeVO> stickTypeVOS = new ArrayList<>();
        if(stickTypes !=null){
            stickTypeVOS = stickTypes.stream().map(StickTypeVO::new).collect(Collectors.toList());
        }
        return Result.success(stickTypeVOS);
    }

    /**
     * 获取地址
     */
    @Transactional
    public Result address(LocationDTO locationDTO) {
        StickAddressVO stickAddressVO = new StickAddressVO();
        CommonService.AddressResult addressDetail = commonService.getAddressDetail(locationDTO.getLatitude(), locationDTO.getLongitude());
        if(addressDetail!=null){
            stickAddressVO.setAddress(addressDetail.getDetailAddress());
            stickAddressVO.setCity(addressDetail.getCity());
            stickAddressVO.setProvince(addressDetail.getProvince());
            stickAddressVO.setDistrict(addressDetail.getDistrict());
        }else {
            return Result.fail("未找到详细地址，请检查经纬度");
        }
        stickAddressVO.setLatitude(locationDTO.getLatitude());
        stickAddressVO.setLongitude(locationDTO.getLongitude());
        return Result.success(stickAddressVO);
    }

    /**
     * 添加贴子
     */
    @Transactional
    public Result addStick(Long userId, StickDto stickDto, HttpServletRequest request){
        StickType stickType = stickTypeRepository.findOne(stickDto.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        // 只有管理员权限能发帖
        if(stickType.getLimitState() != 1){
            return Result.fail("无权操作");
        }
        stickType.setStickCount(stickType.getStickCount()+1);
        stickTypeRepository.save(stickType);
        Stick stick = new Stick();
        StickSampleVO stickSampleVO = new StickSampleVO(controllStick(userId,stickDto,stick,stickType,request));
        return Result.success(stickSampleVO);
    }

    /**
     * 编辑或添加贴子
     */
    @Transactional
    public Stick controllStick(Long userId,StickDto stickDto,Stick stick,StickType stickType, HttpServletRequest request){
        User user = userRepository.findOne(userId);
        stick.setTitle(stickDto.getTitle());
        stick.setContent(stickDto.getContent());
        // 匹配图片
        List<String> images = commonService.image(stickDto.getContent());
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
        stick.setIp(NetworkUtil.getIpAddress(request));
        if (images.size()>9){
            stick.setImages(images.subList(0,9));
        }
        else {
            stick.setImages(images);
        }
        return stickRepository.saveAndFlush(stick);
    }

    /**
     * 编辑贴子
     */
    @Transactional
    public Result updateStick(long userId, long stickId, StickDto stickDto, HttpServletRequest request) {
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("帖子不存在");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被删除或禁用");
        }
        if (stick.getUser().getId() != userId){
            return Result.fail("无权操作");
        }
        if (stick.getLockState() != 1){
            return Result.fail("该贴处于管理员锁住状态，不可评论，编辑或删除");
        }
        StickType stickType = stickTypeRepository.findOne(stickDto.getTypeId());
        if(stickType == null){
            return Result.fail("请选择分类");
        }
        StickSampleVO stickSampleVO = new StickSampleVO(controllStick(userId,stickDto,stick,stickType,request));
        return Result.success(stickSampleVO);
    }


    public Result listStick(String type,int page,Integer limit){
        /* 首页 */
        if(type.equals("config_home")){
            Page<Stick> home = stickRepository.find4Home(stickRepository.pageRequest(page,limit!=null?limit:5));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }
        // 热门
        else if(type.equals("config_hot")){
            Page<Stick> home = stickRepository.find4Hot(stickRepository.pageRequest(page,limit!=null?limit:20));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }
        // 分类贴子列表
        else{
            long typeId = Long.parseLong(type);
            StickType stickType = stickTypeRepository.findOne(typeId);
            Integer defaultSort = stickType.getDefaultSort();
            // 默认时间排序
            String sort = "createTime";
            // 最后回复的帖子排在前面
            if (defaultSort.equals(StickType.defaultSortEnum.COMMENT_COUNT.getId())) {
                sort = "answerCount";
            }
            // 浏览数最多的帖子排在前面
            else if (defaultSort.equals(StickType.defaultSortEnum.READ_COUNT.getId()) ){
                sort = "readCount";
            }
            // 最新发表的帖子排在前面
            else if (defaultSort.equals(StickType.defaultSortEnum.NEW_STICK.getId())){
                sort = "createTime";
            }
            // 回复数最多的帖子排在最前面
            else if (defaultSort.equals(StickType.defaultSortEnum.LAST_COMMENT.getId())){
                sort = "answerTime";
            }
            Page<Stick> home = stickRepository.findType(typeId,stickRepository.pageRequest(page,limit!=null?limit:20,
                    new Sort(Sort.Direction.DESC,sort)));
            PageResult<Stick> pageResult = new PageResult(home);
            return Result.success(pageResult.result2Result(StickSampleVO::new));
        }
    }

    @Transactional
    public Result home(){
        Map<String, List<HomeVO>> banner = getBanner(STICK_HOME.getId());
        return Result.success(banner);
    }

    /**
     *  分类头部信息
     */
    public Result typeDetail(long typeId){
        StickType stickType = stickTypeRepository.findOne(typeId);
        if(stickType==null){
            return Result.fail("分类不存在");
        }
        if (stickType.getState() != 1){
            return Result.fail("该分类已被删除");
        }
        return Result.success(new StickTypeDetailVo(stickType));
    }

    /**
     * 获取帖子详情
     */
    @Transactional
    public Result detail(long userId, long id) {
        Integer flag = 0;
        Stick stick = stickRepository.findById(id);
        if (stick == null){
            return Result.fail("贴子不存在");
        }
        if (stick.getOperationState() != 1){
            return Result.fail("尚未通过审核");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被删除或禁用");
        }
        StickDetailVO stickDetailVO = new StickDetailVO(stick);
        stickDetailVO.setListMap(getBanner(STICK_DETAIL.getId()));
        // 打赏该贴用户列表
        List<StickReward> list = stickRewardResitory.findByStickId(id);
        List<StickRewardUser> stickRewardUserList  = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            stickRewardUserList = list.stream().map(StickRewardUser::new).collect(Collectors.toList());
        }
        stickDetailVO.setStickRewardUserList(stickRewardUserList);

        // 如果该分类贴子需要后台审核
        if (stick.getStickType().getExamineState() == 1){
            if (stick.getOperationState() == 3) {
                return Result.fail("待审核");
            }
            return Result.success(stickDetailVO);
        }
        stick.setReadCount(stick.getReadCount()+1);
        // 该用户是否为该评论点过赞
        StickLike stickLike = stickLikeRepository.findByUserIdAndStickId(userId,id);
        if (stickLike != null){
            if (stickLike.getStatus() == 1 ){
                flag = 1;
            }
        }
        stickDetailVO.setFlag(flag);
        stickRepository.saveAndFlush(stick);
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
            Stick stick = stickRepository.findById(id);
            if (stick == null){
                return Result.fail("点赞失败,贴子不存在");
            }
            if (stick.getState() != 1){
                return Result.fail("贴子已被删除或禁用");
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
            StickComment stickComment = stickCommentRepository.findById(id);
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
     * 添加贴子回复或添加评论回复
     * @param userId 用户id
     * @param stickCommentDTO 请求对象
     * @param request 请求
     * @return Result
     */
    public Result addComment(long userId, StickCommentDTO stickCommentDTO, HttpServletRequest request) {
        // 贴子id
        Stick stick = stickRepository.findById(stickCommentDTO.getStickId());
        if (stick == null){
            return Result.fail("贴子不存在");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被禁用或删除");
        }
        if (stick.getLockState() != 1){
            return Result.fail("该贴处于管理员锁住状态，不可评论，编辑或删除");
        }
        if (stick.getStickType().getAdmitsState() != 1){
            return Result.fail("该分类下贴子不可评论");
        }
        StickComment stickComment = new StickComment();
        // @的用户
        Long notifyUserId = stickCommentDTO.getNotifyUserId();
        if (notifyUserId != null){
            User aitUser = userRepository.findOne(notifyUserId);
            if (aitUser != null){
                stickComment.setNotifyUserId(aitUser);
            }
        }
        // 回复的用户
        User user = userRepository.findOne(userId);
        stickComment.setAnswerUser(user);
        // 回复内容
        stickComment.setContent(stickCommentDTO.getContent());
        // 贴子id
        stickComment.setStick(stick);
        //ip
        stickComment.setIp(NetworkUtil.getIpAddress(request));
        // 增加回复数
        stick.setAnswerCount(stick.getAnswerCount()+1);
        stick.setAnswerTime(new Date());

        // 父级id
        Long stickCommentId = stickCommentDTO.getStickCommentId();
        if (stickCommentId != null){
            stickComment.setLevel(1);
            StickComment stickCommentPid = stickCommentRepository.findById(stickCommentId);
            if(stickCommentPid!=null){
                stickComment.setStickComment(stickCommentPid);
                // 回复楼层
                stickComment.setFloor(0);
            }else {
                return Result.fail("该评论不存在或已被删除");
            }
        }else {
            // 回复楼层
            stickComment.setFloor(stick.getFloorCount()+1);
            stick.setFloorCount(stick.getFloorCount()+1);
        }
        stickRepository.save(stick);
        stickComment  = stickCommentRepository.save(stickComment);

        return Result.success("回复成功",stickComment);
    }

    /**
     * 拿到评论
     */
    public Result getComment(long userId, long stickId, Integer page, Integer limit) {
        PageResult<StickComment> pageResult = stickCommentRepository.findStickCommentList(stickId,page, limit);
        if (pageResult == null){
            return Result.success("暂无评论");
        }
        return Result.success(pageResult.result2Result(el -> stickComent2Vo(userId,el.getStickComment())));
    }
    @Transactional
    public StickCommentVO stickComent2Vo(long userId,StickComment stickComment){
        Integer flag = 0;
        User user = stickComment.getAnswerUser();
        if (user.getId() == null){
            return new StickCommentVO();
        }
        // 拿到回复的回复列表
        List<StickCommentSimpleVO> stickCommentSimpleVOS = new ArrayList<>();
        long stickCommentParentId = stickComment.getId();
        List<StickComment> stickCommentChild= stickCommentRepository.findByBackId(stickCommentParentId);
        if (CollectionUtils.isNotEmpty(stickCommentChild)){
                stickCommentSimpleVOS = stickCommentChild.stream().map(StickCommentSimpleVO::new).collect(Collectors.toList());
        }
        StickCommentVO stickCommentVO = new StickCommentVO(stickComment);
        UserExtends userExtends = userExtendsRepository.findByUserId(user.getId());
        if (userExtends != null){
            stickCommentVO.setSex(userExtends.getSex());
        }
        stickCommentVO.setList(stickCommentSimpleVOS);
        StickCommentLike stickCommentLike = stickCommentLikeRepository.findByUserIdAndStickCommentId(userId,stickComment.getId());
        if (stickCommentLike != null){
            if (stickCommentLike.getStatus() == 1 ){
                flag = 1;
            }
        }
        stickCommentVO.setFlag(flag);
        return stickCommentVO;
    }

    /**
     * 赞赏列表
     */
    public Result getReward(long stickId) {
        List<Config> mapListConfig = configService.getMapListConfig(ParamConstant.REWARD_MONEY);
        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("贴子不存在");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被删除或禁用");
        }
        StickRewardVO stickRewardVO = new StickRewardVO(stick,mapListConfig);
        return Result.success(stickRewardVO);
    }

    /**
     * 赞赏酒元
     */
    public Result rewardJiuyuan(long userId,StickRewardJiuYuanDTO stickRewardJiuYuanDTO) {
        // 酒元icon
        String icon = configService.getStringConfig(JIUYUAN_ICON);
        // 商品类型
        GoodsType goodsType = goodsTypeReposiroty.findByCode(GoodsType.GoodsTypeEnum.STICK_REWARD.getCode());
        if (goodsType == null){
            return Result.fail("商品类型不存在");
        }
        // 前台显示对象
        StickRewardMoneyVO rewardMoneyVO = new StickRewardMoneyVO();
        rewardMoneyVO.setIcon(icon);
        rewardMoneyVO.setRewardMoney(stickRewardJiuYuanDTO.getReward());
        // 类型和标题
        Stick stick = stickRepository.findById(stickRewardJiuYuanDTO.getStickId());
        if (stick != null){
            rewardMoneyVO.setTitle(stick.getTitle());
        }
        rewardMoneyVO.setType(goodsType.getName());
        // 酒元余额
        User user = userRepository.findOne(userId);
        if (user != null){
            UserAccount userAccount = userAccountRepository.findByUserId(user.getId());
            rewardMoneyVO.setBalance(userAccount.getBalance());
            // 留言
            rewardMoneyVO.setWords(stickRewardJiuYuanDTO.getWords());
        }
        return Result.success(rewardMoneyVO);
    }

    /**
     * 打赏
     */
    public Result reward(long userId, StickRewardJiuYuanDTO stickRewardJiuYuanDTO, HttpServletRequest request) {
        if (stickRewardJiuYuanDTO.getReward().signum() != 1 ){
            return Result.fail("金额不能为负数");
        }
        BigDecimal money = stickRewardJiuYuanDTO.getReward();
        // 余额与打赏金额对比
        UserAccount userAccountD = userAccountRepository.findByUserId(userId);
        int flag = userAccountD.getBalance().compareTo(money);
        if (flag == -1){
            return Result.fail("酒元余额不足");
        }
        Long stickId = stickRewardJiuYuanDTO.getStickId();
        // 减
        Result resultDe = commonService.setBalance(userId,money.abs().negate(), BalanceFlow.BalanceFlowTypeEnum.STICK_REWARD.getId(),stickId,"","");
        // 加
        Stick stick = stickRepository.findById(stickId);

        Result resultRe = commonService.setBalance(stick.getUser().getId(),money, BalanceFlow.BalanceFlowTypeEnum.STICK_REWARD.getId(),stickId,"","");
        // 失败
        if(resultRe.getCode()==Result.FAILED_CODE && resultDe.getCode()==Result.FAILED_CODE){
            this.logger.errorf("用户金额打赏失败,msg:{0}",resultRe.getMsg());
            throw new ServiceException("打赏失败");
        }
        StickReward stickReward = new StickReward();
        stickReward.setReward(money);
        stickReward.setStick(stick);
        stickReward.setUser(userRepository.findOne(userId));
        stickReward.setIp(NetworkUtil.getIpAddress(request));
        stickReward.setWords(stickRewardJiuYuanDTO.getWords());
        stickRewardResitory.saveAndFlush(stickReward);
        // 如果留言不为空 增加评论
        if (StringUtils.isNotBlank(stickReward.getWords())){
            StickComment stickComment = new StickComment();
            // 回复的用户
            User user = userRepository.findOne(userId);
            stickComment.setAnswerUser(user);
            // 回复内容
            stickComment.setContent(stickRewardJiuYuanDTO.getWords());
            // 回复楼层
            stickComment.setFloor(stick.getAnswerCount()+1);
            // 贴子id
            stickComment.setStick(stick);
            // ip
            stickComment.setIp(NetworkUtil.getIpAddress(request));
            stickCommentRepository.save(stickComment);
            // 增加阅读数和回复数
            stick.setAnswerCount(stick.getAnswerCount()+1);
            stick.setReadCount(stick.getReadCount()+1);
            stickRepository.save(stick);
        }
        // 更新打赏累计金额
        UserAccount userAccount = userAccountRepository.findByUserId(stick.getUser().getId());
        userAccount.setRewardMoney(userAccount.getRewardMoney().add(money));
        userAccountRepository.save(userAccount);
        // 成功
        return Result.success("打赏成功");
    }

    /**
     * 删除帖子
     */
    public Result delete(long userId, long stickId) {
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("帖子不存在");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被删除或禁用");
        }
        if (stick.getLockState() != 1){
            return Result.fail("该贴处于管理员锁住状态，不可评论，编辑或删除");
        }
        if (stick.getUser().getId() != userId){
            return Result.fail("无权操作");
        }
        stick.setState(3);
        stickRepository.save(stick);
        return Result.success("删除成功");
    }

    /**
     * 帖子评论删除
     */
    public Result commentDelete(long userId, long stickCommentId) {
        StickComment stickComment = stickCommentRepository.findById(stickCommentId);
        if (stickComment == null ){
            return Result.fail("该评论已被删除或禁用");
        }
        if (stickComment.getAnswerUser().getId() != userId){
            return Result.fail("无权操作");
        }
        stickComment.setState(3);
        stickCommentRepository.save(stickComment);
        return Result.success("删除评论成功");
    }

    /**
     * 拿到举报类型
     */
    public Result getReportType() {
        List<String> mapListConfig = configService.getStringListConfig(ParamConstant.STICK_REPORT);
        if(CollectionUtils.isEmpty(mapListConfig)){
            mapListConfig = new ArrayList<>();
        }
        return Result.success(mapListConfig);
    }

    /**
     * 举报
     */
    public Result report(long userId, ReportDTO reportDTO) {
        Long stickId = reportDTO.getStickId();
        String content = reportDTO.getContent();
        Stick stick = stickRepository.findById(stickId);
        if (stick == null){
            return Result.fail("贴子不存在");
        }
        if (stick.getState() != 1){
            return Result.fail("贴子已被禁用或删除");
        }
        StickReport stickReport = new StickReport();
        stickReport.setContent(content);
        stickReport.setStick(stick);
        stickReport.setUserId(userId);
        stickReport.setReportType(reportDTO.getReportType());
        stickReportRepository.save(stickReport);
        return Result.success("举报成功");
    }


}
