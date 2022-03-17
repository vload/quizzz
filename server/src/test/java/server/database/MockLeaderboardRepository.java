package server.database;

import commons.LeaderboardEntry;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockLeaderboardRepository implements LeaderboardRepository {

    public final List<LeaderboardEntry> entries = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }


    @Override
    public List<LeaderboardEntry> findAllByOrderByScoreDesc() {
        return null;
    }

    @Override
    public List<LeaderboardEntry> findAll() {
        return null;
    }

    @Override
    public List<LeaderboardEntry> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<LeaderboardEntry> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<LeaderboardEntry> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(LeaderboardEntry entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends LeaderboardEntry> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends LeaderboardEntry> S save(S entity) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<LeaderboardEntry> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends LeaderboardEntry> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<LeaderboardEntry> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public LeaderboardEntry getOne(Long aLong) {
        return null;
    }

    @Override
    public LeaderboardEntry getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends LeaderboardEntry> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends LeaderboardEntry> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends LeaderboardEntry> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends LeaderboardEntry, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
