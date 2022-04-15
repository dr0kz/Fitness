package com.sorsix.fitness.service

import com.sorsix.fitness.domain.entities.User
import com.sorsix.fitness.domain.entities.UserFollowUser
import com.sorsix.fitness.repository.UserFollowUserRepository
import com.sorsix.fitness.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserFollowUserService(val userFollowUserRepository: UserFollowUserRepository,
                            val userRepository: UserRepository) {

    @Transactional
    fun followUser(followingId: Long, followerId: Long): Boolean {
        val userFollowing: Optional<User> = userRepository.findById(followingId)
        val userFollower: Optional<User> = userRepository.findById(followerId)

        return if(userFollowUserRepository.existsByUserFollowingIdAndUserFollowerId(followingId,followerId)){
            //ako postoi zapis u tabelata deka userFollowing go sledi userFollower togas trgni go toj zapis od tabela
            //isto taka namali mu go brojot na following na userFollowing i save
            //isto taka namali mu go brojot na followers na userFollower i save
            userFollowUserRepository.deleteUserFollowUserByUserFollowerIdAndUserFollowingId(followerId,followingId)
            userRepository.updateNumberOfFollowersMinus(followerId)
            userRepository.updateNumberOfFollowingMinus(followingId)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing.get())
            false
        } else {
            //ako ne postoi zapis u tabelata deka userFollowing go sledi userFollower togas dodadi go toj zapis u tabela
            //isto taka zgolemi mu go brojot na following na userFollowing i save
            //isto taka zgolemi mu go brojot na followers na userFollower i save
            userFollowUserRepository.save(UserFollowUser(0L,userFollowing.get(),userFollower.get()))
            userRepository.updateNumberOfFollowersPlus(followerId)
            userRepository.updateNumberOfFollowingPlus(followingId)
            userRepository.save(userFollower.get())
            userRepository.save(userFollowing.get())
            true
        }
    }
}