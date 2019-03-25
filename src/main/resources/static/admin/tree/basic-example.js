
function listAgent(inviteCode,nickName,avatarUrl,cardNum){
	var uid=inviteCode;
	var config = {
	        container: "#basic-example",
	        
	        connectors: {
	            type: 'step'
	        },
	        node: {
	            HTMLclass: 'nodeExample1'
	        }
	    }
	   u1 = {
	        text: {
	            name: "昵称："+nickName,
	            title: "代理Id:"+inviteCode,
                contact: "房卡数："+cardNum,
	        },
            image: avatarUrl,
	    }
		var chart_config = [
	        config,
	        u1
	    ];
		$.ajax({
			type: "post",
			dataType: "json",
			url: "/kingGameAdmin/agent/findUpAgent",
			data:{inviteCode:uid}, 
			async: false, 
			success : function(data) {
				$.each(data, function(i, n) {
					var u2 = {
					        parent: u1,
					        text:{
					            name: "昵称："+n.nickName,
					            title: "代理Id:"+n.uid,
                                contact: "房卡数："+n.cardNum,
					        },
						    image: n.avatarUrl,
					    }
					chart_config.push(u2);
					$.ajax({
						type: "post",
						dataType: "json", 
						async:false,
						url: "/kingGameAdmin/agent/findUpAgent",
						data:{inviteCode:n.uid}, 
						success : function(data) {
							$.each(data, function(i, n) {
								var u3 = {
								        parent: u2,
								        text:{
								        	name: "昵称："+n.nickName,
								            title: "代理Id:"+n.uid,
                                            contact: "房卡数："+n.cardNum,
								        },
									    image: n.avatarUrl,
								    }
								chart_config.push(u3);
								$.ajax({
									type: "post",
									dataType: "json", 
									async:false,
									url: "/kingGameAdmin/agent/findUpAgent",
									data:{inviteCode:n.uid}, 
									success : function(data) {
										$.each(data, function(i, n) {
											var u4 = {
											        parent: u3,
											        text:{
											        	name: "昵称："+n.nickName,
											            title: "代理Id:"+n.uid,
                                                        contact: "房卡数："+n.cardNum,
											        },
                                                    image: n.avatarUrl,
											    }
											chart_config.push(u4);
											$.ajax({
												type: "post",
												dataType: "json", 
												async:false,
												url: "/kingGameAdmin/agent/findUpAgent",
												data:{inviteCode:n.uid}, 
												success : function(data) {
													$.each(data, function(i, n) {
														var u5 = {
														        parent: u4,
														        text:{
														        	name: "昵称："+n.nickName,
														            title: "代理Id:"+n.uid,
                                                                    contact: "房卡数："+n.cardNum,
														        },
                                                                image: n.avatarUrl,
														    }
														chart_config.push(u5);
														$.ajax({
															type: "post",
															dataType: "json", 
															async:false,
															url: "/kingGameAdmin/agent/findUpAgent",
															data:{inviteCode:n.uid}, 
															success : function(data) {
																$.each(data, function(i, n) {
																	var u6 = {
																	        parent: u5,
																	        text:{
																	        	name: "昵称："+n.nickName,
																	            title: "代理Id:"+n.uid,
                                                                                contact: "房卡数："+n.cardNum,
																	        },
                                                                            image: n.avatarUrl,
																	    }
																	chart_config.push(u6);
																	$.ajax({
																		type: "post",
																		dataType: "json", 
																		async:false,
																		url: "/kingGameAdmin/agent/findUpAgent",
																		data:{inviteCode:n.uid}, 
																		success : function(data) {
																			$.each(data, function(i, n) {
																				var u7 = {
																				        parent: u6,
																				        text:{
																				        	name: "昵称："+n.nickName,
																				            title: "代理Id:"+n.uid,
                                                                                            contact: "房卡数："+n.cardNum,
																				        },
                                                                                        image: n.avatarUrl,
																				    }
																				chart_config.push(u7);
																				$.ajax({
																					type: "post",
																					dataType: "json", 
																					async:false,
																					url: "/kingGameAdmin/agent/findUpAgent",
																					data:{inviteCode:n.uid}, 
																					success : function(data) {
																						$.each(data, function(i, n) {
																							var u8 = {
																							        parent: u7,
																							        text:{
																							        	name: "昵称："+n.nickName,
																							            title: "代理Id:"+n.uid,
                                                                                                        contact: "房卡数："+n.cardNum,
																							        },
                                                                                                    image: n.avatarUrl,
																							    }
																							chart_config.push(u8);
																							$.ajax({
																								type: "post",
																								dataType: "json", 
																								async:false,
																								url: "/kingGameAdmin/agent/findUpAgent",
																								data:{inviteCode:n.uid}, 
																								success : function(data) {
																									$.each(data, function(i, n) {
																										var u9 = {
																										        parent: u8,
																										        text:{
																										        	name: "昵称："+n.nickName,
																										            title: "代理Id:"+n.uid,
                                                                                                                    contact: "房卡数："+n.cardNum,
																										        },
                                                                                                                image: n.avatarUrl,
																										    }
																										chart_config.push(u9);
																										$.ajax({
																											type: "post",
																											dataType: "json", 
																											async:false,
																											url: "/kingGameAdmin/agent/findUpAgent",
																											data:{inviteCode:n.uid}, 
																											success : function(data) {
																												$.each(data, function(i, n) {
																													var u10 = {
																													        parent: u9,
																													        text:{
																													        	name: "昵称："+n.nickName,
																													            title: "代理Id:"+n.uid,
                                                                                                                                contact: "房卡数："+n.cardNum,
																													        },
                                                                                                                            image: n.avatarUrl,
																													    }
																													chart_config.push(u10);
																													$.ajax({
																														type: "post",
																														dataType: "json", 
																														async:false,
																														url: "/kingGameAdmin/agent/findUpAgent",
																														data:{inviteCode:n.uid}, 
																														success : function(data) {
																															$.each(data, function(i, n) {
																																var u11 = {
																																        parent: u10,
																																        text:{
																																        	name: "昵称："+n.nickName,
																																            title: "代理Id:"+n.uid,
                                                                                                                                            contact: "房卡数："+n.cardNum,
																																        },
                                                                                                                                        image: n.avatarUrl,
																																    }
																																chart_config.push(u11);
																																$.ajax({
																																	type: "post",
																																	dataType: "json", 
																																	async:false,
																																	url: "/kingGameAdmin/agent/findUpAgent",
																																	data:{inviteCode:n.uid}, 
																																	success : function(data) {
																																		$.each(data, function(i, n) {
																																			var u12 = {
																																			        parent: u11,
																																			        text:{
																																			        	name: "昵称："+n.nickName,
																																			            title: "代理Id:"+n.uid,
                                                                                                                                                        contact: "房卡数："+n.cardNum,
																																			        },
																																				    image: n.avatarUrl,
																																			    }
																																			chart_config.push(u12);
																																			$.ajax({
																																				type: "post",
																																				dataType: "json", 
																																				async:false,
																																				url: "/kingGameAdmin/agent/findUpAgent",
																																				data:{inviteCode:n.uid}, 
																																				success : function(data) {
																																					$.each(data, function(i, n) {
																																						var u13 = {
																																						        parent: u12,
																																						        text:{
																																						        	name: "昵称："+n.nickName,
																																						            title: "代理Id:"+n.uid,
                                                                                                                                                                    contact: "房卡数："+n.cardNum,
																																						        },
                                                                                                                                                                image: n.avatarUrl,
																																						    }
																																						chart_config.push(u13);
																																					});
																																				}
																																			});
																																		});
																																	}
																																});
																															});
																														}
																													});
																												});
																											}
																										});
																									});
																								}
																							});
																						});
																					}
																				});
																			});
																		}
																	});
																});
															}
														});
													});
												}
											});
										});
									}
								});
							});
						}
					});
				});
			}
		});
		return chart_config;
}