# android-demos


一、为什么使用多进程
1》突破虚拟机分配进程的运行内存限制

2》提高各个进程稳定性，单一进程崩溃后不影响整个程序

3》对内存更可控，通过主动释放进程，减少系统压力，提高系统的流畅度

Error:No toolchains found in the NDK toolchains folder for ABI with prefix: mips64el-linux-android
    原因：
        This version of the NDK is incompatible with the Android Gradle plugin
           version 3.0 or older. If you see an error like
           `No toolchains found in the NDK toolchains folder for ABI with prefix: mips64el-linux-android`,
           update your project file to [use plugin version 3.1 or newer]. You will also
           need to upgrade to Android Studio 3.1 or newer.

        也就是说新版本的NDK与3.0及以前旧版的Android Gradle plugin插件不兼容.
        我本机的studio是3.0.1的所以出现这个问题
    解决方案：
        dependencies {
            classpath 'com.android.tools.build:gradle:3.1.0'

            // NOTE: Do not place your application dependencies here; they belong
            // in the individual module build.gradle files
        }
        ---------------------
        作者：vocanicy
        来源：CSDN
        原文：https://blog.csdn.net/vocanicy/article/details/83004626

报错：
vorg.gradle.api.tasks.TaskExecutionException: Execution failed for task ':app:processDebugManifest'.
 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeActions(ExecuteActionsTaskExecuter.java:100)
 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:70)
 	at org.gradle.api.internal.tasks.execution.OutputDirectoryCreatingTaskExecuter.execute(OutputDirectoryCreatingTaskExecuter.java:51)
 	at org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter.execute(SkipUpToDateTaskExecuter.java:62)
 	at org.gradle.api.internal.tasks.execution.ResolveTaskOutputCachingStateExecuter.execute(ResolveTaskOutputCachingStateExecuter.java:54)
 	at org.gradle.api.internal.tasks.execution.ValidatingTaskExecuter.execute(ValidatingTaskExecuter.java:60)
 	at org.gradle.api.internal.tasks.execution.SkipEmptySourceFilesTaskExecuter.execute(SkipEmptySourceFilesTaskExecuter.java:97)
 	at org.gradle.api.internal.tasks.execution.CleanupStaleOutputsExecuter.execute(CleanupStaleOutputsExecuter.java:87)
 	at org.gradle.api.internal.tasks.execution.ResolveTaskArtifactStateTaskExecuter.execute(ResolveTaskArtifactStateTaskExecuter.java:52)
 	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:52)
 	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:54)
 	at org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter.execute(ExecuteAtMostOnceTaskExecuter.java:43)
 	at org.gradle.api.internal.tasks.execution.CatchExceptionTaskExecuter.execute(CatchExceptionTaskExecuter.java:34)
 	at org.gradle.execution.taskgraph.DefaultTaskGraphExecuter$EventFiringTaskWorker$1.run(DefaultTaskGraphExecuter.java:248)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor$RunnableBuildOperationWorker.execute(DefaultBuildOperationExecutor.java:336)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor$RunnableBuildOperationWorker.execute(DefaultBuildOperationExecutor.java:328)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor.execute(DefaultBuildOperationExecutor.java:199)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor.run(DefaultBuildOperationExecutor.java:110)
 	at org.gradle.execution.taskgraph.DefaultTaskGraphExecuter$EventFiringTaskWorker.execute(DefaultTaskGraphExecuter.java:241)
 	at org.gradle.execution.taskgraph.DefaultTaskGraphExecuter$EventFiringTaskWorker.execute(DefaultTaskGraphExecuter.java:230)
 	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor$TaskExecutorWorker.processTask(DefaultTaskPlanExecutor.java:123)
 	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor$TaskExecutorWorker.access$200(DefaultTaskPlanExecutor.java:79)
 	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor$TaskExecutorWorker$1.execute(DefaultTaskPlanExecutor.java:104)
 	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor$TaskExecutorWorker$1.execute(DefaultTaskPlanExecutor.java:98)
 	at org.gradle.execution.taskgraph.DefaultTaskExecutionPlan.execute(DefaultTaskExecutionPlan.java:626)
 	at org.gradle.execution.taskgraph.DefaultTaskExecutionPlan.executeWithTask(DefaultTaskExecutionPlan.java:581)
 	at org.gradle.execution.taskgraph.DefaultTaskPlanExecutor$TaskExecutorWorker.run(DefaultTaskPlanExecutor.java:98)
 	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:63)
 	at org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:46)
 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
 	at org.gradle.internal.concurrent.ThreadFactoryImpl$ManagedThreadRunnable.run(ThreadFactoryImpl.java:55)
 	at java.lang.Thread.run(Thread.java:745)
 Caused by: java.lang.RuntimeException: Manifest merger failed with multiple errors, see logs
 	at com.android.builder.core.AndroidBuilder.mergeManifestsForApplication(AndroidBuilder.java:524)
 	at com.android.build.gradle.tasks.MergeManifests.doFullTaskAction(MergeManifests.java:143)
 	at com.android.build.gradle.internal.tasks.IncrementalTask.taskAction(IncrementalTask.java:106)
 	at sun.reflect.GeneratedMethodAccessor257.invoke(Unknown Source)
 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
 	at java.lang.reflect.Method.invoke(Method.java:498)
 	at org.gradle.internal.reflect.JavaMethod.invoke(JavaMethod.java:73)
 	at org.gradle.api.internal.project.taskfactory.IncrementalTaskAction.doExecute(IncrementalTaskAction.java:46)
 	at org.gradle.api.internal.project.taskfactory.StandardTaskAction.execute(StandardTaskAction.java:39)
 	at org.gradle.api.internal.project.taskfactory.StandardTaskAction.execute(StandardTaskAction.java:26)
 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter$1.run(ExecuteActionsTaskExecuter.java:121)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor$RunnableBuildOperationWorker.execute(DefaultBuildOperationExecutor.java:336)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor$RunnableBuildOperationWorker.execute(DefaultBuildOperationExecutor.java:328)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor.execute(DefaultBuildOperationExecutor.java:199)
 	at org.gradle.internal.progress.DefaultBuildOperationExecutor.run(DefaultBuildOperationExecutor.java:110)
 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeAction(ExecuteActionsTaskExecuter.java:110)
 	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeActions(ExecuteActionsTaskExecuter.java:92)
 	... 32 more

解决方式：
打开AndroidManifest，切换到Merged Manifest，可以查看报错信息
