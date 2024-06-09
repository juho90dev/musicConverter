package com.jh.musicConverter.util;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pager {
	
	public static int PAGE_SCALE=10;
	public static int BLOCK_SCALE=10;
	
	private int curPage; // 현재 페이지
	private int prevPage; // 이전 페이지
	private int nextPage; // 다음 페이지
	private int totPage; // 전체 페이지 갯수
	private int totBlock;//전체 페이지블록 갯수
	private int curBlock; // 전체 페이지 블록 갯수
	private int prevBlock; // 이전 페이지 블록
	private int nextBlock; // 다음 페이지 블록
	private int pageBegin; 
	private int pageEnd;
	private int blockStart; // 페이지블로의 첫 페이지 번호
	private int blockEnd; // 페이지블로의 마지막 페이지 번호
	
	// 레코드 갯수,페이지 번호
	public Pager(int count, int curPage) {
		curBlock = 1;
		this.curPage=curPage;
		
	}
	
	// 페이지 블록의 범위
	public void setBlockRange() {
		//(현재 페이지-1)/페이지 블록 단위+1
		curBlock=(int)Math.ceil((curPage-1) / BLOCK_SCALE)+1;
		// (현재 블록-1)*블록단위+1
		blockStart=(curBlock-1) * BLOCK_SCALE + 1;
		// 블록시작번호*(페이지블록단위-1)
		blockEnd=blockStart + (BLOCK_SCALE-1);
		// 블록의 마지막 페이지번호가 범위를 초과하지 않도록
		if(blockEnd > totPage){
			blockEnd = totPage;
		}
		// 이전을 눌렀을때 이동할 페이지
		prevPage = curPage - 1;
		// 다음을 눌렀을때 이동할 페이지
		nextPage = curPage + 1;
		
		if(nextPage == totPage) {
			totPage += 1;
		}
		
	}
	public void setPageRange() {
		// 시작번호 = (현재 페이지 - 1) * 페이지당 게시물 수 + 1
		pageBegin = (curPage - 1) * PAGE_SCALE + 1;
		// 끝번호 = 시작번호 + (페이지당 게시물 수 - 1) 
		pageEnd = pageBegin + (PAGE_SCALE -1);
	}
	
	//전체 페이지 갯수 
	public void setTotPage(int count) {
		totPage = (int)Math.ceil(count * 1.0/PAGE_SCALE);
	}
	
	public void setTotBlock() {
		totBlock = (int)Math.ceil(totPage*1.0/BLOCK_SCALE);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
