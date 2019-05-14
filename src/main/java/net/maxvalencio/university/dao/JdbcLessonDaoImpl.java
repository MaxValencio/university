package net.maxvalencio.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.maxvalencio.university.dao.interfaces.LessonDAO;
import net.maxvalencio.university.domain.Audience;
import net.maxvalencio.university.domain.Discipline;
import net.maxvalencio.university.domain.Group;
import net.maxvalencio.university.domain.Lesson;
import net.maxvalencio.university.domain.Teacher;

import static net.maxvalencio.university.dao.DAOUtils.*;

public class JdbcLessonDaoImpl implements LessonDAO {

    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Lesson create(Discipline discipline, String dateStart,
            String dateEnd, Audience audience, Teacher teacher,
            List<Group> groups) {
        final String SQL_INSERT_LESSONS = "INSERT INTO lessons(discipline_id, date_start, date_end, audience_id, teacher_id) "
                + "VALUES (?, ?, ?, ?, ?);";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_LESSONS,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, discipline.getId());
            statement.setString(2, dateStart);
            statement.setString(3, dateEnd);
            statement.setLong(4, audience.getId());
            statement.setLong(5, teacher.getId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            long lessonId = resultSet.getLong("id");
            insertLessonsGroups(connection, lessonId, groups);
            return getById(lessonId);
        } catch (SQLException e) {
            System.err.println(
                    "Error in create() method of JdbcLessonDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private void insertLessonsGroups(Connection connection, long lessonId,
            List<Group> groups) throws SQLException {
        final String SQL_INSERT_LESSONS_GROUPS = "INSERT INTO lessons_groups(lesson_id, group_id) VALUES(?,?);";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_LESSONS_GROUPS);
            for (Group group : groups) {
                statement.setLong(1, lessonId);
                statement.setLong(2, group.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error inserting into the lessons_groups table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public Lesson getById(long id) {
        final String SQL_SELECT_LESSONS = "SELECT * FROM lessons WHERE id = ?;";

        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_LESSONS);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lesson = new Lesson();
                lesson.setId(resultSet.getLong("id"));

                long disciplineId = resultSet.getLong("discipline_id");
                JdbcDisciplineDaoImpl daoDiscipline = new JdbcDisciplineDaoImpl();
                lesson.setDiscipline(daoDiscipline.getById(disciplineId));

                lesson.setDateStart(resultSet.getString("date_start"));
                lesson.setDateEnd(resultSet.getString("date_end"));

                long audienceId = resultSet.getLong("audience_id");
                JdbcAudienceDaoImpl daoAudience = new JdbcAudienceDaoImpl();
                lesson.setAudience(daoAudience.getById(audienceId));

                long teacherId = resultSet.getLong("teacher_id");
                JdbcTeacherDaoImpl daoTeacher = new JdbcTeacherDaoImpl();
                lesson.setTeacher(daoTeacher.getById(teacherId));

                lesson.setGroups(getGroups(connection, id));
                return lesson;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in getById() method of JdbcLessonDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }

    private List<Group> getGroups(Connection connection, long id)
            throws SQLException {
        final String SQL_SELECT_LESSONS_GROUPS = "SELECT * FROM lessons_groups WHERE lesson_id = ?;";

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_LESSONS_GROUPS);
            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            List<Group> groups = new ArrayList<>();
            JdbcGroupDaoImpl daoGroup = new JdbcGroupDaoImpl();

            while (resultSet.next()) {
                long groupId = resultSet.getLong("group_id");
                Group group = daoGroup.getById(groupId);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            System.err.println("Error selecting from the lessons_groups table");
            throw new SQLException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
    }

    @Override
    public Lesson update(long id, Discipline discipline, String dateStart,
            String dateEnd, Audience audience, Teacher teacher,
            List<Group> groups) {
        final String SQL_UPDATE_LESSONS = "UPDATE lessons SET discipline_id = ?, date_start = ?, date_end = ?, "
                + "audience_id = ?, teacher_id = ? WHERE id = ?;";

        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_LESSONS);
            statement.setLong(1, discipline.getId());
            statement.setString(2, dateStart);
            statement.setString(3, dateEnd);
            statement.setLong(4, id);
            statement.executeQuery();
            updateLessonsGroups(connection, id, groups);
            lesson = getById(id);
            return lesson;
        } catch (SQLException e) {
            System.err.println(
                    "Error in update() method of JdbcLessonDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return null;
    }

    private void updateLessonsGroups(Connection connection, long id,
            List<Group> groups) throws SQLException {
        final String SQL_UPDATE_LESSONS_GROUPS = "UPDATE lessons_groups SET group_id = ? WHERE lesson_id = ?;";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_LESSONS_GROUPS);
            for (Group group : groups) {
                statement.setLong(1, group.getId());
                statement.setLong(2, id);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error selecting from the lessons_groups table");
            throw new SQLException(e);
        } finally {
            closeStatement(statement);
        }
    }

    @Override
    public boolean delete(long id) {
        final String SQL_DELETE = "DELETE FROM lessons WHERE id = " + id + ";";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(SQL_DELETE);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println(
                    "Error in delete() method of JdbcLessonDaoImpl class");
        } finally {
            close(statement, connection);
        }
        return false;
    }

    @Override
    public List<Lesson> getAll() {
        final String SQL_SELECT = "SELECT id FROM lessons;";

        List<Lesson> lessons = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);

            lessons = new ArrayList<>();

            while (resultSet.next()) {
                long lessonId = resultSet.getLong("id");
                Lesson lesson = getById(lessonId);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            System.err.println(
                    "Error in getAll() method of JdbcLessonDaoImpl class");
        } finally {
            close(resultSet, statement, connection);
        }
        return null;
    }
}
