package com.h9.common.db.repo;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @Description: TODO
 * @author Javen Xiong
 * @date 2016年10月13日
 *
 */

@Repository
@Transactional
public class JpaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> int count(Class<T> type) {
		EntityManager em = getEntityManager();
		CriteriaQuery<Long> cq = em.getCriteriaBuilder()
				.createQuery(Long.class);
		Root<T> rt = cq.from(type);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public <T> T create(final T entity) {
		EntityManager em = getEntityManager();
		return em.merge(entity);
	}

	public <T> void persist(final T entity) {
		EntityManager em = getEntityManager();
		em.persist(entity);
	}

	public <T> void update(final T entity) {
		EntityManager em = getEntityManager();
		em.merge(entity);
		em.flush();
	}
	
	public <T> T merge(final T entity) {
		EntityManager em = getEntityManager();
		return em.merge(entity);
	}

	public int update(String qlString) {
		Query query = getEntityManager().createQuery(qlString);
		return query.executeUpdate();
	}

	public <T> void delete(final T entity) throws NoResultException {
		EntityManager em = getEntityManager();
		em.remove(entity);
	}

	public <T> T deleteById(final Class<T> type, final Integer id)
			throws NoResultException {
		T object = findById(type, id);
		delete(object);
		return object;
	}
	
	public <T> T deleteById(final Class<T> type, final Long id)
			throws NoResultException {
		T object = findById(type, id);
		delete(object);
		return object;
	}

	public <T> T deleteUniqByProp(final Class<T> type, String key,String value)
			throws NoResultException {
		T object = this.findUniqueByProp(type, key, value);
		delete(object);
		return object;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> createQuery(Class<T> type, String sql) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(sql);
		q.setHint("org.hibernate.cacheable", true);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T findById(final Class<T> type, final Integer id)
			throws NoResultException {
		Class<?> clazz = getObjectClass(type);
		EntityManager em = getEntityManager();
		T result = (T) em.find(clazz, id);
		return result;
	}
	@SuppressWarnings("unchecked")
	public <T> T findById(final Class<T> type, final Long id)
			throws NoResultException {
		Class<?> clazz = getObjectClass(type);
		EntityManager em = getEntityManager();
		T result = (T) em.find(clazz, id);
		if (result == null) {
			throw new NoResultException("No object of type: " + type
					+ " with ID: " + id);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByProps(final Class<T> type, final String[] keys,Object... values) {
		try{
			if(keys!=null&&keys.length>0){
				Class<?> clazz = getObjectClass(type);
				EntityManager em = getEntityManager();
				StringBuilder ql = new StringBuilder("select o from "+clazz.getSimpleName()+" o where ");
				for(int i=0;i<keys.length;i++){
					ql.append("o.");
					ql.append(keys[i].toString());
					ql.append("=");
					ql.append(values[i]);
					if(i!=keys.length-1){
						ql.append(" and ");
					}
				}
				Query query = em.createQuery(ql.toString());
				List<T> objects = query.getResultList();
				return objects;
			}
			
		}catch(NoResultException e){
			return null;
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T findUniqueByProps(final Class<T> type, final String[] keys,Object... values) {
		List<T> objs = this.findByProps(type, keys, values);
		if(objs!=null&&objs.size()>0){
			return objs.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByProp(final Class<T> type, final String key,String value) {
		try{
			Class<?> clazz = getObjectClass(type);
			EntityManager em = getEntityManager();
			String ql = "select o from "+clazz.getSimpleName()+" o where o."+key+" ="+value;
			Query query = em.createQuery(ql);
			List<T> objects = query.getResultList();
			return objects;
		}catch(NoResultException e){
			return null;
		}
		
	}
	@SuppressWarnings("unchecked")
	public <T> T findUniqueByProp(final Class<T> type, final String key,String value){
		List<T> objects = this.findByProp(type, key, value);
		if(objects!=null&&objects.size()>=1)
			return objects.get(0);
		return null;
	}

	public <T> int count(Class<T> type, String sql) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(sql);
		return q.getResultList().size();
	}

	public long count(String sql) {
		EntityManager em = getEntityManager();
		sql="select count(*) "+sql.substring(sql.indexOf("from"));
		Query q = em.createQuery(sql);
		return (long)q.getSingleResult();
	}
	
	public long distinctCount(String sql,String key) {
		EntityManager em = getEntityManager();
		sql="select count(distinct "+key+") "+sql.substring(sql.indexOf("from"));
		Query q = em.createQuery(sql);
		return (long)q.getSingleResult();
	}
	
	public BigInteger nativeCount(String sql) {
		EntityManager em = getEntityManager();
		sql="select count(*) "+sql.substring(sql.indexOf("from"));
		Query q = em.createNativeQuery(sql);
		return (BigInteger)q.getSingleResult();
	}
	
	public BigInteger nativeCount(String countKey,String sql) {
		EntityManager em = getEntityManager();
		sql="select count("+countKey+") "+sql.substring(sql.indexOf("from"));
		Query q = em.createNativeQuery(sql);
		return (BigInteger)q.getSingleResult();
	}
	
	public long qlCount(String sql) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(sql);
		return (long)q.getSingleResult();
	}
	
	public BigDecimal sum(String sql,String sumStr) {
		EntityManager em = getEntityManager();
		sql="select sum("+sumStr+") "+sql.substring(sql.indexOf("from"));
		Query q = em.createQuery(sql);
		return (BigDecimal)q.getSingleResult();
	}
	
	public BigDecimal nativeSum(String sql,String sumStr) {
		EntityManager em = getEntityManager();
		sql="select sum("+sumStr+") "+sql.substring(sql.indexOf("from"));
		Query q = em.createNativeQuery(sql);
		return (BigDecimal)q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> createQuery(Class<T> type, String sql, int firstResult,
			int maxResults) {
		EntityManager em = getEntityManager();
		Query q = em.createQuery(sql);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> createNativeQuery(Class<T> type, String sql,
			int firstResult, int maxResults) {
		EntityManager em = getEntityManager();
		Query q = em.createNativeQuery(sql);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List createNativeQuery( String sql,
			int firstResult, int maxResults) {
		EntityManager em = getEntityManager();
		Query q = em.createNativeQuery(sql);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public  List createMapNativeQuery(String sql,Object... params) {
		EntityManager em = getEntityManager();
		Query q = em.createNativeQuery(sql);
		int i = 1;
		for (Object param : params) {
			q.setParameter(i++, param);
		}
		q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return  q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public  List createNativeQuery(String sql,Object... params) {
		EntityManager em = getEntityManager();
		Query q = em.createNativeQuery(sql);
		int i = 1;
		for (Object param : params) {
			q.setParameter(i++, param);
		}
		return  q.getResultList();
	}
	public <T> List<T> findAll(final Class<T> type) {
		EntityManager em = getEntityManager();
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(type);
		query.from(type);
		return em.createQuery(query).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> type, int firstResult, int maxResults) {
		EntityManager em = getEntityManager();
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(type);
		cq.select(cq.from(type));
		Query q = em.createQuery(cq);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> type, int firstResult, int maxResults,String attr,String orderBy) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = criteriaBuilder.createQuery(type);
		Root<T> routeRoot = cq.from(type);
		cq.select(routeRoot);
		if(orderBy!=null&&orderBy.equals("desc")){
			cq.orderBy(criteriaBuilder.desc(routeRoot.get(attr)));
		}else{
			cq.orderBy(criteriaBuilder.asc(routeRoot.get(attr)));
		}
		return this.findAll(em, cq, firstResult, maxResults);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(Class<T> type, int firstResult, int maxResults,final String[] attr,Object... orderBy) {
		EntityManager em = getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = criteriaBuilder.createQuery(type);
		Root<T> routeRoot = cq.from(type);
		cq.select(routeRoot);
		int j;
		for(int i=0;i<attr.length;i++){
			j=orderBy.length>=i?0:i;
			if(orderBy[j]!=null&&orderBy[j].equals("desc")){
				cq.orderBy(criteriaBuilder.desc(routeRoot.get(attr[i])));
			}else{
				cq.orderBy(criteriaBuilder.asc(routeRoot.get(attr[i])));
			}
		}
		return this.findAll(em, cq, firstResult, maxResults);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> findAll(EntityManager em, CriteriaQuery<T> cq, int firstResult, int maxResults) {
		Query q = em.createQuery(cq);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findByQuery(final String hql,
			final Object... params) {
		EntityManager em = getEntityManager();
		Query query = em.createQuery(hql);
		int i = 1;
		for (Object p : params) {
			query.setParameter(i++, p);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(final String namedQueryName) {
		EntityManager em = getEntityManager();
		return em.createNamedQuery(namedQueryName).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQuery(final String namedQueryName,
			final Object... params) {
		EntityManager em = getEntityManager();
		Query query = em.createNamedQuery(namedQueryName);
		int i = 1;
		for (Object p : params) {
			query.setParameter(i++, p);
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findByNamedQueryNameValue(final String namedQueryName,
			Object... nameValues) {
		EntityManager em = getEntityManager();
		Query query = em.createNamedQuery(namedQueryName);
		if (nameValues != null) {
			int len = nameValues.length;
			if ((len & 1) == 0) { // 命名查询“参数名个数+参数值个数”必须为偶数
				for (int i = 0; i < len; i += 2) {
					query.setParameter((String) nameValues[i],
							nameValues[i + 1]);
				}
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQuery(final String namedQueryName)
			throws NoResultException {
		EntityManager em = getEntityManager();
		return (T) em.createNamedQuery(namedQueryName).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByNamedQuery(final String namedQueryName,
			final Object... params) throws NoResultException {
		EntityManager em = getEntityManager();
		Query query = em.createNamedQuery(namedQueryName);
		int i = 1;
		for (Object p : params) {
			query.setParameter(i++, p);
		}
		return (T) query.getSingleResult();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	public <T> T findUniqueByQuery(final String queryString,
			final Object... params) {
		EntityManager em = getEntityManager();
		Query query = em.createQuery(queryString);
		int i = 1;
		for (Object p : params) {
			query.setParameter(i++, p);
		}
		try {
			return (T) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("" + e);
		}
		return null;
	}

	public <T> void refresh(final T entity) {
		getEntityManager().refresh(entity);
	}

	public <T> void save(final T entity) {
		EntityManager em = getEntityManager();
		em.merge(entity);
	}

	public <T> T saveWithReturnEntity(final T entity) {
		EntityManager em = getEntityManager();
		T t = em.merge(entity);
		return t;
	}

	/**
	 * 判断值是否存在
	 * 
	 * @param ejbql
	 * @param params
	 * @return
	 */
	public boolean isResultExist(String ejbql, Object... params) {
		Query q = getEntityManager().createQuery(ejbql);
		int i = 1;
		for (Object param : params) {
			q.setParameter(i++, param);
		}
		List<?> l = q.getResultList();
		return !(l.isEmpty());

	}

	// 自定义sql范围查询。
	@SuppressWarnings("unchecked")
	public <T> List<T> findResultByRange(Class<T> type, String ejbql,
			int firstResult, int maxResults) {
		Query q = getEntityManager().createQuery(ejbql);
		q.setMaxResults(maxResults);
		q.setFirstResult(firstResult);
		return q.getResultList();
	}

	private Class<?> getObjectClass(final Object type)
			throws IllegalArgumentException {
		Class<?> clazz = null;
		if (type == null) {
			throw new IllegalArgumentException(
					"Null has no type. You must pass an Object");
		} else if (type instanceof Class<?>) {
			clazz = (Class<?>) type;
		} else {
			clazz = type.getClass();
		}
		return clazz;
	}
	
	@Transactional
    public <T> void batchAdd(List<T> list) {
		EntityManager em = getEntityManager();
		int batchSize = 100;
		int size;
        for (int i = 0; i < list.size(); i+=batchSize) {
        	size = i+batchSize>list.size()?(list.size()%batchSize):batchSize;
        	for(int j=i;j<i+size;j++){
        		 em.persist(list.get(j));
        	}
            em.flush();
            em.clear();
        }
    }
}