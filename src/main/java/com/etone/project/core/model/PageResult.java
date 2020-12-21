package com.etone.project.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;
/**
 * mybatis版分页返回值.
 * 
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 14169 $$
 * @date 2013-9-6
 */
public class PageResult<T> implements Serializable {
	private static final long serialVersionUID = -2266436707999984552L;
	protected int pageNo = 1;
	protected int pageSize = -1;
	protected boolean autoCount = true;

	protected List<T> result = new ArrayList();
	protected long totalItems = -1L;

	public PageResult() {
	}

	public PageResult(int pageSize) {
		setPageSize(pageSize);
	}

	public PageResult(int pageNo, int pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1)
			this.pageNo = 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		if (this.pageNo > getTotalPages()) {
			this.pageNo = (int) getTotalPages();
		}
		int offset = this.pageNo == 0 ? 0 : (this.pageNo - 1) * this.pageSize;
		return offset;
	}

	public int getStartRow() {
		return getOffset() + 1;
	}

	public int getEndRow() {
		long actual = this.pageSize * this.pageNo;
		actual = actual > this.totalItems ? this.totalItems : actual;
		Long actualResult = Long.valueOf(actual);
		return Integer.parseInt(actualResult.toString());
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public Iterator<T> iterator() {
		return this.result == null ? IteratorUtils.EMPTY_ITERATOR : this.result
				.iterator();
	}

	public long getTotalItems() {
		return this.totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public boolean isLastPage() {
		return this.pageNo == getTotalPages();
	}

	public boolean isHasNextPage() {
		return this.pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNextPage()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isFirstPage() {
		return this.pageNo == 1;
	}

	public boolean isHasPrePage() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPrePage()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}

	public int getTotalPages() {
		if (this.totalItems < 0L) {
			return -1;
		}

		long count = this.totalItems / this.pageSize;
		if (this.totalItems % this.pageSize > 0L) {
			count += 1;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	public List<Long> getSlider(int count) {
		int halfSize = count / 2;
		long totalPage = getTotalPages();

		long startPageNumber = Math.max(this.pageNo - halfSize, 1);
		long endPageNumber = Math.min(startPageNumber + count - 1L, totalPage);

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = Math.max(endPageNumber - count, 1L);
		}

		List result = new ArrayList();
		for (long i = startPageNumber; i <= endPageNumber; i += 1L) {
			result.add(new Long(i));
		}
		return result;
	}
}