package com.vanhack.jobmatch.useful;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class CommonDAO<T>{

	private Class<T> persistentClass;
	
	public void set(Class<T> persistentClass) {
		 this.persistentClass = persistentClass;
		
	  }

	  public EntityManager getEntityManager() {
			return EntityManagerHelper.getEntityManager();
	  }
	  
	  
	  public void save(T entity) {
			try {
				getEntityManager().persist(entity);
			} catch (RuntimeException re) {
				throw re;
			}
	  }
	  
	  public void saveObject(Object entity) {
				try {
					getEntityManager().persist(entity);
				} catch (RuntimeException re) {
					throw re;
				}
		}
	  
	  public void delete(final Integer id) {
			
			if(id != null) {
				try {				
					
					T entity = getEntityManager().getReference(persistentClass, id);
					getEntityManager().remove(entity);
					
				} catch (RuntimeException re) {
					throw re;
				}
			}
			
		}
	  
	  public void delete(String id) {
			
			if(id != null) {
				try {				
					
					T entity = getEntityManager().getReference(persistentClass, id);
					getEntityManager().remove(entity);
					
				} catch (RuntimeException re) {
					throw re;
				}
			}
			
		}
	  
	  public int nextId() {
		  Integer count = this.countAll();
		  count++;
		  return count;
	  }
	  
	  public int nextId(Class myPersistentClass) {
		  Integer count = this.countAll(myPersistentClass);
		  count++;
		  return count;
	  }
	  
	  private int countAll(Class myPersistentClass) {
			
			try {
				final String queryString = "select count(p.id) from " + myPersistentClass.getName() + " p";
				Query query = getEntityManager().createQuery(queryString);
				Long total = (Long) query.getSingleResult();
				return Integer.parseInt(total.toString());
			} catch (RuntimeException re) {
				throw re;
			}
		}
	  
	  public int countAll() {
			
			try {
				final String queryString = "select count(p.id) from " + persistentClass.getName() + " p";
				Query query = getEntityManager().createQuery(queryString);
				Long total = (Long) query.getSingleResult();
				return Integer.parseInt(total.toString());
			} catch (RuntimeException re) {
				throw re;
			}
		}
	  
	  public List<T> findAll(final int... rowStartIdxAndCount) {
			
			try {
				final String queryString = "select model from "+ persistentClass.getName() + " model order by id";
				Query query = getEntityManager().createQuery(queryString);
				if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
					int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
					if (rowStartIdx > 0) {
						query.setFirstResult(rowStartIdx);
					}
					if (rowStartIdxAndCount.length > 1) {
						int rowCount = Math.max(0, rowStartIdxAndCount[1]);
						if (rowCount > 0) {
							query.setMaxResults(rowCount);
						}
					}
				}
				return query.getResultList();
			} catch (RuntimeException re) {
				
				throw re;
			}		
		}
	  
	  public T findById(final Object id) {
			try {
				T instance = getEntityManager().find(persistentClass, id);
				return instance;
			} catch (RuntimeException re) {
				throw re;
			}
		}
	  
	  /**
		 * Find all Area entities with a specific property value.
		 * 
		 * @param propertyName
		 *            the name of the Area property to query
		 * @param value
		 *            the property value to match
		 * @param rowStartIdxAndCount
		 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
		 *            row index in the query result-set to begin collecting the
		 *            results. rowStartIdxAndCount[1] specifies the the maximum
		 *            number of results to return.
		 * @return List<Area> found by query
		 */
		public List<T> findByProperty(final String propertyName, Object value,
				int... rowStartIdxAndCount) {
			// TODO Auto-generated method stub
			try {
				final String queryString = "select model from " + persistentClass.getName() + " model where model."
						+ propertyName + "= :propertyValue";
				Query query = getEntityManager().createQuery(queryString);
				query.setParameter("propertyValue", value);
				if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
					int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
					if (rowStartIdx > 0) {
						query.setFirstResult(rowStartIdx);
					}

					if (rowStartIdxAndCount.length > 1) {
						int rowCount = Math.max(0, rowStartIdxAndCount[1]);
						if (rowCount > 0) {
							query.setMaxResults(rowCount);
						}
					}
				}
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		/**
		 * 
		 * 
		 * @param propertyName
		 *            the name of the Area property to query
		 * @param value
		 *            the property value to match
		 * @param rowStartIdxAndCount
		 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the
		 *            row index in the query result-set to begin collecting the
		 *            results. rowStartIdxAndCount[1] specifies the the maximum
		 *            number of results to return.
		 * @return List<Area> found by query
		 */
		public List<T> findByProperty(final Map<String, Object> properties,
				int... rowStartIdxAndCount) {
			// TODO Auto-generated method stub
			String log = "finding " + persistentClass.getName() + " instance with properties: ";
			for(String key: properties.keySet()) {
				log += key + ", value: " + properties.get(key);
			}
		
			try {
				String queryString = "select model from " + persistentClass.getName() + " model where ";
				int i = 0;
				for(String key: properties.keySet()) {
					if(i == 0) {
						queryString += "model." + key + "= :propertyValue" + i;
					}
					else {
						queryString += " and model." + key + "= :propertyValue" + i;
					}
					i++;
				}
				Query query = getEntityManager().createQuery(queryString);
				i = 0;
				for(String key: properties.keySet()) {
					query.setParameter("propertyValue" + i, properties.get(key));
					i++;
				}
				if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
					int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
					if (rowStartIdx > 0) {
						query.setFirstResult(rowStartIdx);
					}

					if (rowStartIdxAndCount.length > 1) {
						int rowCount = Math.max(0, rowStartIdxAndCount[1]);
						if (rowCount > 0) {
							query.setMaxResults(rowCount);
						}
					}
				}
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		public int countByProperty(final String propertyName, Object value) {
			try {
				final String queryString = "select count(model.id) from " + persistentClass.getName() + " model where model."
				+ propertyName + "= :propertyValue";
				//final String queryString = "select count(p.id) from " + persistentClass.getName() + " p";
				Query query = getEntityManager().createQuery(queryString);
				query.setParameter("propertyValue", value);
				Long total = (Long) query.getSingleResult();
				return Integer.parseInt(total.toString());
			} catch (RuntimeException re) {
				throw re;
			}
		}
		
		public List<T> query(String sentenceJpa) {
			try {
				
				Query query = getEntityManager().createQuery(sentenceJpa);
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}	
		}
		
		
		public List<T> query(String sentenceJpa, Object... objects) {
			try {
				Query query = getEntityManager().createQuery(sentenceJpa);
				for(int i=0;i<objects.length;i++){
					int j=i+1;
					query.setParameter(j, objects[i]);
				}			
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}	
		}
		
		public List<T> queryNative(String sentenceSql) {
			try {
				Query query = getEntityManager().createNativeQuery(sentenceSql);
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}	
		}
		
		public List<T> queryNative(String sentenceSql, Class c) {
			try {
				Query query = getEntityManager().createNativeQuery(sentenceSql,c);
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}	
		}
		
		public List<T> queryNative(String sentenceSql, Object... objects) {
			
			try {
				Query query = getEntityManager().createNativeQuery(sentenceSql);
				for(int i=0;i<objects.length;i++){
					int j=i+1;
					query.setParameter(j, objects[i]);
				}
				return query.getResultList();
			} catch (RuntimeException re) {
				throw re;
			}	
		}
}
