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

@Service
@Api(name = "quizService", namespace = @ApiNamespace(ownerDomain = "org.zezutom", ownerName = "org.zezutom"),
        version = AppUtil.API_VERSION,
        clientIds = {Ids.WEB, Ids.ANDROID, API_EXPLORER_CLIENT_ID},
        audiences = {Ids.WEB, Ids.ANDROID},
        scopes = {Scopes.EMAIL, Scopes.PROFILE})
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizRatingRepository quizRatingRepository;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Transactional
    @Override
    @ApiMethod(path = "quiz/add", httpMethod = ApiMethod.HttpMethod.POST)
    public void addNew(User user, Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Transactional
    @Override
    @ApiMethod(path = "quiz/rate", httpMethod = ApiMethod.HttpMethod.POST)
    public void rate(User user, @Named("quizId") String quizId, @Named("liked") boolean liked) {
        Quiz quiz = quizRepository.findOne(AppUtil.sanitize(quizId));
        if (liked) quiz.upVote(); else quiz.downVote();
        quizRepository.save(quiz);

        QuizRating quizRating = quizRatingRepository.findOneByQuizId(quiz.getId());
        if (quizRating == null)
            quizRating = createQuizRating(quiz);
        else
            quizRating = updateQuizRating(quizRating, quiz);

        quizRatingRepository.save(quizRating);
    }

    private QuizRating createQuizRating(Quiz quiz) {
        QuizRating quizRating = new QuizRating();
        quizRating.setQuizId(quiz.getId());
        quizRating.setUpVotes(quiz.getUpVotes());
        quizRating.setDownVotes(quiz.getDownVotes());
        quizRating.setRatingCount(quiz.getRatingCount());
        return quizRating;
    }

    private QuizRating updateQuizRating(QuizRating quizRating, Quiz quiz) {
        quizRating.setUpVotes(quiz.getUpVotes());
        quizRating.setDownVotes(quiz.getDownVotes());
        quizRating.setRatingCount(quiz.getRatingCount());
        return quizRating;
    }

}
