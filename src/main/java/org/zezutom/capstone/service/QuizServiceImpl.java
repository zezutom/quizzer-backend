package org.zezutom.capstone.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zezutom.capstone.dao.QuizRatingRepository;
import org.zezutom.capstone.dao.QuizRepository;
import org.zezutom.capstone.model.Quiz;
import org.zezutom.capstone.model.QuizRating;
import org.zezutom.capstone.model.QuizSelectionCriteria;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID;

@Transactional
@Service
@Api(name = "quizService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class QuizServiceImpl extends GAEService implements QuizService {

    @PersistenceContext(unitName = "jpa.unit")
    private EntityManager em;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

    @Override
    @ApiMethod(path = "quiz/get/all", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    @ApiMethod(path = "quiz/get/criteria", httpMethod = ApiMethod.HttpMethod.GET)
    public List<Quiz> getByCriteria(QuizSelectionCriteria criteria) {

        final String queryString = "select q from Quiz q where q.difficulty in " +
                inEnum(criteria.getDifficultyLevels()) +
                " and q.category in " + inEnum(criteria.getCategories()) + " order by q.question";
        Query query = em.createQuery(queryString);

        return query.getResultList();
    }

    @Override
    @ApiMethod(path = "quiz/add", httpMethod = ApiMethod.HttpMethod.POST)
    public Quiz addNew(User user, Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    @ApiMethod(path = "quiz/rate", httpMethod = ApiMethod.HttpMethod.POST)
    public QuizRating rate(User user, @Named("quizId") String quizId, @Named("liked") boolean liked) {
        QuizRating rating = new QuizRating();
        rating.setQuizId(quizId);
        rating.setLiked(liked);
        return quizRatingRepository.save(rating);
    }

    private<T extends Enum> String inEnum(List<T> enumList) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (T item : enumList) {
            sb.append(item.ordinal()).append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(")");
        return sb.toString();

    }
}
