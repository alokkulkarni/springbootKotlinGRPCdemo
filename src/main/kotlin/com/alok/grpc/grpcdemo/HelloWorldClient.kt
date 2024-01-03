//package com.alok.grpc.grpcdemo
//
//import io.grpc.ManagedChannel
//import io.grpc.ManagedChannelBuilder
//import io.grpc.xds.internal.security.Closeable
//import java.util.concurrent.TimeUnit
//
//class HelloWorldClient(private val managedChannel: ManagedChannel): Closeable {
//
//    private val stub: GreeterGrpcKt.GreeterCoroutineStub = GreeterGrpcKt.GreeterCoroutineStub(managedChannel)
//
//    suspend fun greet(name: String) {
//        val request = HelloRequest.newBuilder().setName(name).build()
//        val response = stub.sayHello(request)
//        println("Received: ${response.message}")
//    }
//
//    override fun close() {
//        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
//    }
//}
//
///**
// * Greeter, uses first argument as name to greet if present;
// * greets "world" otherwise.
// */
//suspend fun main(args: Array<String>) {
//    val port = 50051
//
//    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
//
//    val client = HelloWorldClient(channel)
//
//    val user = args.singleOrNull() ?: "world"
//    client.greet(user)
//}