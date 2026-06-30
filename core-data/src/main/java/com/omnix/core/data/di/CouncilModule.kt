package com.omnix.core.data.di

import com.omnix.core.data.council.CouncilAgent
import com.omnix.core.data.council.agents.CreativeAgent
import com.omnix.core.data.council.agents.CriticAgent
import com.omnix.core.data.council.agents.EngineerAgent
import com.omnix.core.data.council.agents.ModeratorAgent
import com.omnix.core.data.council.agents.PlannerAgent
import com.omnix.core.data.council.agents.ResearcherAgent
import com.omnix.core.data.council.agents.SafetyAgent
import com.omnix.core.data.council.agents.SkepticAgent
import com.omnix.core.data.council.agents.WriterAgent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * Registers every AI Council agent into a multibound `Set<CouncilAgent>`.
 * To add a new specialist later: implement [CouncilAgent], add one @Binds
 * function here with @IntoSet. Nothing else in the orchestrator, registry,
 * or UI needs to change.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class CouncilModule {

    @Binds
    @IntoSet
    abstract fun bindPlannerAgent(agent: PlannerAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindEngineerAgent(agent: EngineerAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindResearcherAgent(agent: ResearcherAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindCreativeAgent(agent: CreativeAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindWriterAgent(agent: WriterAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindCriticAgent(agent: CriticAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindSkepticAgent(agent: SkepticAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindSafetyAgent(agent: SafetyAgent): CouncilAgent

    @Binds
    @IntoSet
    abstract fun bindModeratorAgent(agent: ModeratorAgent): CouncilAgent
}
