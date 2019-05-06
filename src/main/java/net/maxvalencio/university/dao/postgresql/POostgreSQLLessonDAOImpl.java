package net.maxvalencio.university.dao.postgresql;

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

public class POostgreSQLLessonDAOImpl implements LessonDAO {
    
    private DAOFactory daoFactory = new DAOFactory();

    @Override
    public Lesson create(Discipline discipline, String dateStart, String dateEnd,
            Audience audience, Teacher teacher, List<Group> groups) {
        String sqlLessons = "INSERT INTO lessons(discipline_id, date_start, date_end, audience_id, teacher_id) "
                + "VALUES (?, ?, ?, ?, ?);";
        String sqlLessonsGroups = "INSERT INTO lessons_groups(lesson_id, group_id) VALUES(?,?);";
        
        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement insertLessons = null;
        PreparedStatement insertLessonsGroups = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            insertLessons = connection.prepareStatement(sqlLessons, Statement.RETURN_GENERATED_KEYS);
            insertLessons.setLong(1, discipline.getId());
            insertLessons.setString(2, dateStart);
            insertLessons.setString(3, dateEnd);
            insertLessons.setLong(4, audience.getId());
            insertLessons.setLong(5, teacher.getId());
            insertLessons.executeUpdate();

            resultSet = insertLessons.getGeneratedKeys();
            resultSet.next();
            long generatedId = resultSet.getLong("id");

            insertLessonsGroups = connection.prepareStatement(sqlLessonsGroups);
            for (Group group : groups) {
                insertLessonsGroups.setLong(1, generatedId);
                insertLessonsGroups.setLong(2, group.getId());
                insertLessonsGroups.addBatch();
            }
            insertLessonsGroups.executeBatch();

            lesson = getById(generatedId);
            return lesson;
        } catch (SQLException e) {
            System.err.println("Error in create() method of PostgrSQLLessonDAOImpl class");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (insertLessons != null) {
                    insertLessons.close();
                }
                if (insertLessonsGroups != null) {
                    insertLessonsGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in create() "
                        + "method of PostgreSQLLessonDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Lesson getById(long id) {
        String sqlLessons = "SELECT * FROM lessons WHERE id = ?;";
        String sqlLessonsGroups = "SELECT * FROM lessons_groups WHERE lesson_id = ?;";
        
        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement selectLessons = null;
        PreparedStatement selecеtLessonsGroups = null;
        ResultSet rsLessons = null;
        ResultSet rsLessonsGroups = null;

        try {
            connection = daoFactory.getConnection();
            selectLessons = connection.prepareStatement(sqlLessons);
            selectLessons.setLong(1, id);

            rsLessons = selectLessons.executeQuery();

            if (rsLessons.next()) {
                lesson = new Lesson();
                lesson.setId(rsLessons.getLong("id"));
                
                long disciplineId = rsLessons.getLong("discipline_id");
                PostgreSQLDisciplineDAOImpl daoDiscipline = new PostgreSQLDisciplineDAOImpl();
                
                lesson.setDiscipline(daoDiscipline.getById(disciplineId));
                lesson.setDateStart(rsLessons.getString("date_start"));
                lesson.setDateEnd(rsLessons.getString("date_end"));
                
                long audienceId = rsLessons.getLong("audience_id");
                PostgreSQLAudienceDAOImpl daoAudience = new PostgreSQLAudienceDAOImpl();
                
                lesson.setAudience(daoAudience.getById(audienceId));
                
                long teacherId = rsLessons.getLong("teacher_id");
                PostgreSQLTeacherDAOImpl daoTeacher = new PostgreSQLTeacherDAOImpl();
                
                lesson.setTeacher(daoTeacher.getById(teacherId));

                selecеtLessonsGroups = connection.prepareStatement(sqlLessonsGroups);
                selecеtLessonsGroups.setLong(1, id);

                rsLessonsGroups = selecеtLessonsGroups.executeQuery();

                List<Group> groups = new ArrayList<>();
                PostgreSQLGroupDAOImpl daoGroup = new PostgreSQLGroupDAOImpl();

                while (rsLessonsGroups.next()) {
                    long groupId = rsLessonsGroups.getLong("group_id");
                    Group group = daoGroup.getById(groupId);
                    groups.add(group);
                }
                
                lesson.setGroups(groups);
                return lesson;
            }
        } catch (SQLException e) {
            System.err.println("Error in getById() method of PostgrSQLLessonDAOImpl class");
        } finally {
            try {
                if (rsLessons != null) {
                    rsLessons.close();
                }
                if (selectLessons != null) {
                    selectLessons.close();
                }
                if (rsLessonsGroups != null) {
                    rsLessonsGroups.close();
                }
                if (selecеtLessonsGroups != null) {
                    selecеtLessonsGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getById() "
                        + "method of PostgreSQLLessonDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public Lesson update(long id, Discipline discipline, String dateStart, String dateEnd,
            Audience audience, Teacher teacher, List<Group> groups) {
        String sqlLessons = "UPDATE lessons SET discipline_id = ?, date_start = ?, date_end = ?, "
                + "audience_id = ?, teacher_id = ? WHERE id = ?;";
        String sqlLessonsGroups = "UPDATE lessons_groups SET group_id = ? WHERE lesson_id = ?;";

        Lesson lesson = null;
        Connection connection = null;
        PreparedStatement updateLessons = null;
        PreparedStatement updateLessonsGroups = null;
        try {
            connection = daoFactory.getConnection();
            updateLessons = connection.prepareStatement(sqlLessons);
            updateLessons.setLong(1, discipline.getId());
            updateLessons.setString(2, dateStart);
            updateLessons.setString(3, dateEnd);
            updateLessons.setLong(4, id);
            updateLessons.executeQuery();

            updateLessonsGroups = connection.prepareStatement(sqlLessonsGroups);
            for (Group group : groups) {
                updateLessonsGroups.setLong(1, group.getId());
                updateLessonsGroups.setLong(2, id);
                updateLessonsGroups.addBatch();
            }
            updateLessonsGroups.executeBatch();

            lesson = getById(id);
            return lesson;
        } catch (SQLException e) {
            System.err.println("Error in update() method of PostgrSQLLessonDAOImpl class");
        } finally {
            try {
                if (updateLessons != null) {
                    updateLessons.close();
                }
                if (updateLessonsGroups != null) {
                    updateLessonsGroups.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in update() "
                        + "method of PostgreSQLLessonDAOImpl class");
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM lessons WHERE id = " + id + ";"; 
        
        Connection connection = null;
        Statement statement = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
            if (i == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in delete() method of PostgrSQLLessonDAOImpl class");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in delete() "
                        + "method of PostgreSQLLessonDAOImpl class");
            }
        }
        return false;
    }

    @Override
    public List<Lesson> getAll() {
        String sql = "SELECT id FROM lessons;";

        List<Lesson> lessons = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            lessons = new ArrayList<>();

            while (resultSet.next()) {
                long lessonId = resultSet.getLong("id");
                Lesson lesson = getById(lessonId);
                lessons.add(lesson);
            }
            return lessons;
        } catch (SQLException e) {
            System.err.println("Error in getAll() method of PostgrSQLLessonDAOImpl class");
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Cannot execute close connection in getAll() "
                        + "method of PostgreSQLLessonDAOImpl class");
            }
        }
        return null;
    }
}
