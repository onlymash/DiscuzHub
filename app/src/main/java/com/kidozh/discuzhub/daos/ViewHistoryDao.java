package com.kidozh.discuzhub.daos;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kidozh.discuzhub.entities.ViewHistory;

import java.util.List;


@Dao
public interface ViewHistoryDao {

    @Query("SELECT * FROM ViewHistory ORDER BY recordAt DESC")
    LiveData<List<ViewHistory>> getAllViewHistory();

    @Query("SELECT * FROM ViewHistory ORDER BY recordAt DESC")
    DataSource.Factory<Integer,ViewHistory> getViewHistoryPageList();


    @Query("SELECT * FROM ViewHistory WHERE belongedBBSId=:bbsId ORDER BY recordAt DESC")
    LiveData<List<ViewHistory>> getAllViewHistoryByBBSId(int bbsId);

    @Query("SELECT * FROM ViewHistory WHERE belongedBBSId=:bbsId ORDER BY recordAt DESC")
    DataSource.Factory<Integer,ViewHistory> getViewHistoryPageListByBBSId(int bbsId);

    @Query("SELECT * FROM ViewHistory " +
            "WHERE (belongedBBSId=:bbsId AND (name LIKE '%' || :text || '%' OR description LIKE '%' || :text || '%' ))" +
            " ORDER BY recordAt DESC")
    DataSource.Factory<Integer,ViewHistory> getViewHistoryPageListByBBSIdWithSearchText(int bbsId, String text);

    @Query("SELECT * FROM ViewHistory WHERE (belongedBBSId=:bbsId AND fid=:fid) ORDER BY recordAt DESC")
    List<ViewHistory> getViewHistoryByBBSIdAndFid(int bbsId, int fid);

    @Query("SELECT * FROM ViewHistory WHERE (belongedBBSId=:bbsId AND tid=:tid) ORDER BY recordAt DESC")
    List<ViewHistory> getViewHistoryByBBSIdAndTid(int bbsId, int tid);

    @Insert
    public void insert(ViewHistory... viewHistories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ViewHistory viewHistory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<ViewHistory> viewHistories);

    @Query("DELETE FROM VIEWHISTORY")
    void deleteAllViewHistory();

    @Query("DELETE FROM VIEWHISTORY WHERE belongedBBSId=:bbsId")
    void deleteViewHistoryByBBSId( int bbsId);

    @Delete
    public void delete(ViewHistory viewHistory);

    @Delete
    public void delete(ViewHistory... viewHistories);
}
