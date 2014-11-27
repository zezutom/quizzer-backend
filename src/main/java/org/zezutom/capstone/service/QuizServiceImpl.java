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
import org.zezutom.capstone.domain.Quiz;
import org.zezutom.capstone.domain.QuizRating;
import org.zezutom.capstone.util.AppUtil;
import org.zezutom.capstone.util.Ids;
import org.zezutom.capstone.util.Scopes;

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
    @ApiMethod(path = "quiz/add", httpMethod = ApiMethod.HttpMethod.POST)
    public void addNew(User user, Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    @ApiMethod(path = "quiz/rate", httpMethod = ApiMethod.HttpMethod.POST)
    public void rate(User user, @Named("quizId") String quizId, @Named("liked") boolean liked) {
        QuizRating rating = new QuizRating();
        rating.setQuizId(quizId);
        rating.setLiked(liked);
        quizRatingRepository.save(rating);
    }
}
